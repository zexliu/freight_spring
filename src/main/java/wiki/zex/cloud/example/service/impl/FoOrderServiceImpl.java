package wiki.zex.cloud.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import wiki.zex.cloud.example.async.DeliveryGoodsAsyncTask;
import wiki.zex.cloud.example.config.MqConfig;
import wiki.zex.cloud.example.entity.*;
import wiki.zex.cloud.example.enums.OrderType;
import wiki.zex.cloud.example.enums.UserType;
import wiki.zex.cloud.example.exception.ForbiddenException;
import wiki.zex.cloud.example.exception.NotFoundException;
import wiki.zex.cloud.example.mapper.FoOrderMapper;
import wiki.zex.cloud.example.message.OrderCreatedMessage;
import wiki.zex.cloud.example.req.FoOrderReq;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.resp.OrderDetails;
import wiki.zex.cloud.example.resp.OrderResp;
import wiki.zex.cloud.example.resp.PushOrderMsg;
import wiki.zex.cloud.example.resp.SimpleDriverResp;
import wiki.zex.cloud.example.security.MyUserDetails;
import wiki.zex.cloud.example.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wiki.zex.cloud.example.utils.DecimalUtils;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-07-17
 */
@Service
public class FoOrderServiceImpl extends ServiceImpl<FoOrderMapper, FoOrder> implements IFoOrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DeliveryGoodsAsyncTask deliveryGoodsAsyncTask;

    @Autowired
    private IFoDeliverGoodsService iFoDeliverGoodsService;

    @Autowired
    private IFoOrderProcessService iFoOrderProcessService;

    @Autowired
    private IRefundOrderService iRefundOrderService;

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private IPayOrderService iPayOrderService;

    @Autowired
    private IFoTransactionService iFoTransactionService;
    @Override
    public IPage<SimpleDriverResp> orderedDrivers(Pageable pageable, Long id) {
        return baseMapper.orderedDrivers(pageable.convert(), id);
    }

    @Override
    @Transactional
    public FoOrder create(FoOrderReq req, MyUserDetails myUserDetails, boolean isUser) {

        FoDeliverGoods deliverGoods = iFoDeliverGoodsService.getById(req.getDeliveryId());
        if (deliverGoods == null) {
            throw new NotFoundException();
        }
        FoOrder foOrder = new FoOrder();
        BeanUtils.copyProperties(req, foOrder);
        foOrder.setUserId(deliverGoods.getUserId());
        foOrder.setDriverPayStatus(!isUser); //司机支付状态
        save(foOrder);
        deliverGoods.setStatus(0);
        iFoDeliverGoodsService.updateById(deliverGoods);

        PushOrderMsg pushOrderMsg = new PushOrderMsg();
        pushOrderMsg.setAmount(foOrder.getAmount());
        pushOrderMsg.setCarLongs(deliverGoods.getCarLongs());
        pushOrderMsg.setCarModels(deliverGoods.getCarModels());
        pushOrderMsg.setDeliveryId(deliverGoods.getId());
        pushOrderMsg.setFreightAmount(req.getFreightAmount());
        pushOrderMsg.setLoadProvinceCode(deliverGoods.getLoadProvinceCode());
        pushOrderMsg.setLoadCityCode(deliverGoods.getLoadCityCode());
        pushOrderMsg.setLoadDistrictCode(deliverGoods.getLoadDistrictCode());
        pushOrderMsg.setUnloadCityCode(deliverGoods.getUnloadCityCode());
        pushOrderMsg.setUnloadProvinceCode(deliverGoods.getUnloadProvinceCode());
        pushOrderMsg.setUnloadDistrictCode(deliverGoods.getUnloadDistrictCode());
        pushOrderMsg.setMobile(myUserDetails.getMobile());
        pushOrderMsg.setNickname(myUserDetails.getNickname());
        pushOrderMsg.setOrderId(foOrder.getId());
        pushOrderMsg.setVolume(deliverGoods.getVolume());
        pushOrderMsg.setWeight(deliverGoods.getWeight());
        pushOrderMsg.setLoadStartAt(deliverGoods.getLoadStartAt());
        pushOrderMsg.setLoadEndAt(deliverGoods.getLoadEndAt());
        deliveryGoodsAsyncTask.pushOrder(isUser,isUser ? foOrder.getDriverId() : foOrder.getUserId(), pushOrderMsg);
        if (isUser) {
            //待支付定金 定时任务
            OrderCreatedMessage message = new OrderCreatedMessage();
            message.setOrderId(foOrder.getId());
            rabbitTemplate.convertAndSend(MqConfig.DElAY_PAY_EXCHANGE, MqConfig.DElAY_PAY_QUEUE, message, processor -> {
                processor.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                processor.getMessageProperties().setDelay(30 * 60 * 1000); //30分钟...
                return processor;
            });
        }
        iFoOrderProcessService.createProcess(foOrder.getId(), foOrder, isUser ? foOrder.getUserId() : foOrder.getDriverId(), isUser ? UserType.USER : UserType.DRIVER, isUser ? OrderType.CREATE_ORDER : OrderType.DRIVER_CREATE_ORDER, null);
        return foOrder;
    }

    @Override
    @Transactional
    public FoOrder confirm(Long id, Authentication authentication) {
        FoOrder foOrder = getById(id);
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        if (!foOrder.getUserId().equals(myUserDetails.getId())){
            throw new ForbiddenException();
        }
        if (foOrder.getConfirmStatus()){
            throw new ForbiddenException();
        }

        foOrder.setConfirmStatus(true);
        updateById(foOrder);
        iFoOrderProcessService.createProcess(id,foOrder,myUserDetails.getId(),UserType.USER,OrderType.USER_CONFIRM_ORDER, null);
        return foOrder;
    }

    @Override
    public IPage<OrderResp> list(Page<OrderResp> page, Long deliveryId, Long userId, Long driverId, Boolean confirmStatus, Boolean transportStatus, Boolean payStatus, Boolean evaluateStatus, Boolean driverEvaluateStatus, Boolean cancelStatus, Boolean refundStatus,  Boolean driverPayStatus, Boolean protocolStatus) {
        return baseMapper.list(page, deliveryId, userId, driverId, confirmStatus, transportStatus, payStatus, evaluateStatus, driverEvaluateStatus, cancelStatus, refundStatus, driverPayStatus,protocolStatus);
    }

    @Override
    public OrderDetails details(Long id) {
        return baseMapper.details(id);
    }

    @Override
    public FoOrder cancel(Long id, Authentication authentication, String description) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        FoOrder foOrder = validOrder(id);

        if (StringUtils.equals(myUserDetails.getClientId(),"master_client")){
            if (!foOrder.getUserId().equals(myUserDetails.getId())) {
                throw new ForbiddenException();
            }
        }else {
            if (!foOrder.getDriverId().equals(myUserDetails.getId())) {
                throw new ForbiddenException();
            }
        }

        if (foOrder.getCancelStatus() || foOrder.getDriverPayStatus()) {
            throw new ForbiddenException();
        }
        foOrder.setCancelStatus(true);
        updateById(foOrder);

        iFoOrderProcessService.createProcess(foOrder.getId(), foOrder, foOrder.getUserId(), UserType.USER, OrderType.USER_CANCEL_ORDER, description);
        deliveryGoodsAsyncTask.pushCancelOrder(foOrder,myUserDetails);
        return foOrder;
    }

    @Override
    @Transactional
    public FoOrder refund(Long id, Authentication authentication, String description) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();


        FoOrder foOrder = validOrder(id);
        if (!foOrder.getUserId().equals(myUserDetails.getId())) {
            throw new ForbiddenException();
        }
        if (foOrder.getRefundStatus() || foOrder.getCancelStatus() || !foOrder.getDriverPayStatus()) {
            throw new ForbiddenException();
        }
        BigDecimal balance = iFoTransactionService.balance(null, null, null, null, myUserDetails.getId());

        if (DecimalUtils.lt(balance,foOrder.getAmount())){
            throw new ForbiddenException("余额不足");
        }
        FoTransaction transaction = new FoTransaction();
        transaction.setAmount(foOrder.getAmount());
        transaction.setIncrStatus(false);
        transaction.setTargetId(foOrder.getId());
        transaction.setType(2);
        transaction.setUserId(myUserDetails.getId());
        iFoTransactionService.save(transaction);

        RefundRequest refundRequest = new RefundRequest();
        PayOrder payOrder = iPayOrderService.getByFoOrderId(id);
        refundRequest.setPayTypeEnum(BestPayTypeEnum.getByName(payOrder.getChannelType()));
        refundRequest.setOrderId(payOrder.getId().toString());
        refundRequest.setOrderAmount(foOrder.getAmount().doubleValue());
        RefundResponse refund = bestPayService.refund(refundRequest);
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setAmount(BigDecimal.valueOf(refund.getOrderAmount()));
        refundOrder.setChannelType(payOrder.getChannelType());
        refundOrder.setDescription(description);
        refundOrder.setOrderId(payOrder.getId());
        refundOrder.setStatus(true);
        refundOrder.setThirdPartyId(refund.getOutRefundNo());
        refundOrder.setUserId(payOrder.getUserId());
        iRefundOrderService.save(refundOrder);
        foOrder.setRefundStatus(true);
        updateById(foOrder);
        iFoOrderProcessService.createProcess(foOrder.getId(), foOrder, foOrder.getUserId(), UserType.USER, OrderType.USER_REFUND_DRIVER_ORDER, description);
        return foOrder;
    }


    private FoOrder validOrder(Long id) {

        FoOrder foOrder = getById(id);
        if (foOrder == null) {
            throw new NotFoundException("订单不存在");
        }
        return foOrder;
    }
}
