package wiki.zex.cloud.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import wiki.zex.cloud.example.async.DeliveryGoodsAsyncTask;
import wiki.zex.cloud.example.entity.FoDeliverGoods;
import wiki.zex.cloud.example.entity.FoProtocol;
import wiki.zex.cloud.example.entity.PayOrder;
import wiki.zex.cloud.example.enums.PayType;
import wiki.zex.cloud.example.exception.ForbiddenException;
import wiki.zex.cloud.example.mapper.FoDeliverGoodsMapper;
import wiki.zex.cloud.example.req.FoDeliverGoodsReq;
import wiki.zex.cloud.example.req.FoOrderReq;
import wiki.zex.cloud.example.resp.FoDeliverDetails;
import wiki.zex.cloud.example.resp.FoDeliverGoodsResp;
import wiki.zex.cloud.example.security.MyUserDetails;
import wiki.zex.cloud.example.service.IFoDeliverGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wiki.zex.cloud.example.service.IFoOrderService;
import wiki.zex.cloud.example.service.IPayOrderService;
import wiki.zex.cloud.example.utils.NetWorkUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 发货信息 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-07-08
 */
@Service
public class FoDeliverGoodsServiceImpl extends ServiceImpl<FoDeliverGoodsMapper, FoDeliverGoods> implements IFoDeliverGoodsService {

    @Autowired
    private DeliveryGoodsAsyncTask deliveryGoodsAsyncTask;

    @Autowired
    private IPayOrderService iPayOrderService;

//
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private IFoOrderService iFoOrderService;
    @Override
    public FoDeliverGoods create(FoDeliverGoodsReq req, Authentication authentication) {
        MyUserDetails userDetails = ((MyUserDetails)authentication.getPrincipal());
        FoDeliverGoods goods = new FoDeliverGoods();
        goods.setUserId(userDetails.getId());
        fitData(req, goods);
        save(goods);
        if (req.getDriverId() == null){
            deliveryGoodsAsyncTask.pushGoodsToClient(goods);
        }else {
            //指定司机
            FoOrderReq orderReq = new FoOrderReq();
            orderReq.setDeliveryId(goods.getId());
            orderReq.setAmount(req.getAmount());
            orderReq.setFreightAmount(req.getFreightAmount());
            orderReq.setDriverId(req.getDriverId());
            iFoOrderService.create(orderReq,userDetails,true);
        }
        return goods;
    }

    @Override
    public FoDeliverGoods update(Long id, FoDeliverGoodsReq req, Authentication authentication) {
        MyUserDetails userDetails = ((MyUserDetails)authentication.getPrincipal());
        FoDeliverGoods goods = getById(id);
        if (!goods.getUserId().equals(userDetails.getId())){
            throw new ForbiddenException();
        }
        fitData(req, goods);
        updateById(goods);
        deliveryGoodsAsyncTask.pushGoodsToClient(goods);
        return goods;
    }

    @Override
    public IPage<FoDeliverGoodsResp> queryList(Page<FoDeliverGoods> page, Long userId, Integer status,
                                               Integer deleteStatus, Integer markStatus, LocalDateTime startAt,
                                               LocalDateTime endAt, LocalDateTime loadStartAt, LocalDateTime loadEndAt, String loadProvinceCode, String loadCityCode,
                                               String loadDistrictCode, String unloadProvinceCode, String unloadCityCode,
                                               String unloadDistrictCode, String carType,
                                               String weights, String carLongs, String carModels, String categoryName, String order) {

        List<Map<String,Integer>> weightList = null;
        if (StringUtils.isNotBlank(weights) ){
            weightList = new ArrayList<>();
            String[] weight  = weights.split(",");
            for (String s : weight) {
                String[] temp = s.split("-");
                if (temp.length == 2){
                    Map<String,Integer> map = new HashMap<>();
                    map.put("ge",Integer.parseInt(temp[0]));
                    map.put("le",Integer.parseInt(temp[1]));
                    weightList.add(map);
                }
            }

        }
        List<Double> longs = null;
        if (StringUtils.isNotBlank(carLongs)){
            String[] split = carLongs.split(",");
            longs = Arrays.stream(split).map(Double::parseDouble).collect(Collectors.toList());;
        }

        List<String> models = null;
        if (StringUtils.isNotBlank(carModels)){
            models = Arrays.asList(carModels.split(","));
        }

        List<String> categories = null;
        if (StringUtils.isNotBlank(categoryName)){
            categories = Arrays.asList(categoryName.split(","));
        }
        return baseMapper.queryList(page,userId,status,deleteStatus,markStatus,startAt,endAt,loadStartAt,loadEndAt,loadProvinceCode,loadCityCode,loadDistrictCode,unloadProvinceCode,unloadCityCode,unloadDistrictCode,
                carType,weightList,longs,models,categories,order);
    }

    @Override
    public FoDeliverDetails details(Long id) {
        return baseMapper.details(id);
    }

    @Override
    public PayResponse driverPay(Long id, Authentication authentication, BestPayTypeEnum channelType, BigDecimal amount, BigDecimal freightAmount, HttpServletRequest request) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        FoDeliverGoods foDeliverGoods = getById(id);
        if (foDeliverGoods.getStatus() != 1){
            throw new ForbiddenException("当前货源不在有效状态");
        }
        PayOrder payOrder = new PayOrder();
        payOrder.setAmount(amount);
        payOrder.setBody(String.valueOf(freightAmount));
        payOrder.setChannelType(channelType.getCode());
        payOrder.setCreateAt(LocalDateTime.now());
        payOrder.setFoDeliveryId(id);
        payOrder.setOrderType(PayType.DRIVER_GOODS_DEPOSIT);
        payOrder.setStatus(0);
        payOrder.setSubject("支付定金");
        payOrder.setUserId(myUserDetails.getId());
        iPayOrderService.save(payOrder);

        PayRequest payRequest = new PayRequest();
        payRequest.setAttach(payOrder.getBody());
        payRequest.setOrderName(payOrder.getSubject());
        payRequest.setOrderAmount(payOrder.getAmount().doubleValue());
        payRequest.setOrderId(payOrder.getId().toString());
        payRequest.setSpbillCreateIp(NetWorkUtil.getRequestIp(request));
        return bestPayService.pay(payRequest);
    }

    @Override
    public FoProtocol getByOrderId(Long orderId) {
        return baseMapper.getByOrderId(orderId);
    }

    private void fitData(FoDeliverGoodsReq req, FoDeliverGoods goods) {
        BeanUtils.copyProperties(req,goods);
        if (CollectionUtils.isNotEmpty(req.getCarLongs())) {
            String str = Joiner.on(",").join(req.getCarLongs());
            goods.setCarLongs(str);
        }
        if (CollectionUtils.isNotEmpty(req.getCarModels())) {
            String str = Joiner.on(",").join(req.getCarModels());
            goods.setCarModels(str);
        }
        if (CollectionUtils.isNotEmpty(req.getRequireList())) {
            String str = Joiner.on(",").join(req.getRequireList());
            goods.setRequireList(str);
        }
    }
}
