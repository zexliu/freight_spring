package wiki.zex.cloud.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import wiki.zex.cloud.example.constants.RedisKeys;
import wiki.zex.cloud.example.entity.FoOrder;
import wiki.zex.cloud.example.entity.PayOrder;
import wiki.zex.cloud.example.entity.SyUser;
import wiki.zex.cloud.example.mapper.PayOrderMapper;
import wiki.zex.cloud.example.req.FoOrderReq;
import wiki.zex.cloud.example.security.MyUserDetails;
import wiki.zex.cloud.example.service.IFoOrderService;
import wiki.zex.cloud.example.service.IPayOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wiki.zex.cloud.example.service.ISyUserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static wiki.zex.cloud.example.enums.PayType.DRIVER_GOODS_DEPOSIT;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-07-30
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IFoOrderService iFoOrderService;

    @Autowired
    private IPayOrderService iPayOrderService;

    @Autowired
    private ISyUserService iSyUserService;
    @Override
    public void onPayHook(PayResponse response) {
        //防止 微信 支付宝 重复 调用
        String repeatKey = stringRedisTemplate.opsForValue().get(RedisKeys.notifyRepeatKey(response.getOutTradeNo()));
        if (repeatKey != null) {
           return;
        }
        //设置五秒的超时时间
        stringRedisTemplate.opsForValue().set(RedisKeys.notifyRepeatKey(response.getOutTradeNo()),
                response.getOutTradeNo(), 5L, TimeUnit.SECONDS);

        PayOrder payOrder = getById(response.getOrderId());
        if (payOrder.getStatus() == 1){
            return;
        }
        payOrder.setStatus(1);
        payOrder.setPayAt(LocalDateTime.now());

        if (payOrder.getOrderType() == DRIVER_GOODS_DEPOSIT){
            FoOrderReq foOrderReq = new FoOrderReq();
            foOrderReq.setDriverId(payOrder.getUserId());
            foOrderReq.setFreightAmount(new BigDecimal(payOrder.getBody()));
            foOrderReq.setAmount(payOrder.getAmount());
            foOrderReq.setDeliveryId(payOrder.getFoDeliveryId());
            SyUser syUser = iSyUserService.getById(payOrder.getUserId());
            MyUserDetails myUserDetails = new MyUserDetails();
            BeanUtils.copyProperties(syUser,myUserDetails);
            FoOrder foOrder = iFoOrderService.create(foOrderReq, myUserDetails, false);
            payOrder.setFoOrderId(foOrder.getId());
        }


        iPayOrderService.updateById(payOrder);

    }

    @Override
    public PayOrder getByFoOrderId(Long id) {
        return getOne(new LambdaQueryWrapper<PayOrder>().eq(PayOrder::getFoOrderId,id));
    }
}
