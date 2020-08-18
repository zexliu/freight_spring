package wiki.zex.cloud.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wiki.zex.cloud.example.entity.FoOrder;
import wiki.zex.cloud.example.entity.FoProtocol;
import wiki.zex.cloud.example.req.FoOrderReq;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.resp.OrderDetails;
import wiki.zex.cloud.example.resp.OrderResp;
import wiki.zex.cloud.example.resp.SimpleDriverResp;
import wiki.zex.cloud.example.security.MyUserDetails;
import wiki.zex.cloud.example.service.IFoOrderService;

import javax.validation.Valid;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/api/v1/orders")
public class FoOrderController {

    @Autowired
    private IFoOrderService iFoOrderService;

    //有过交易的司机
    @GetMapping("/drivers")
    @PreAuthorize("isAuthenticated()")
    public IPage<SimpleDriverResp> orderedDrivers(Authentication authentication, Pageable pageable) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        return iFoOrderService.orderedDrivers(pageable, myUserDetails.getId());
    }

    @GetMapping("/{id}")
    public OrderDetails details(@PathVariable Long id) {
        return iFoOrderService.details(id);
    }

    @GetMapping
    public IPage<OrderResp> list(Pageable pageable,
                                 Long deliveryId, Long userId, Long driverId, Boolean confirmStatus, Boolean transportStatus, Boolean payStatus, Boolean evaluateStatus, Boolean driverEvaluateStatus, Boolean cancelStatus, Boolean refundStatus
            , Boolean driverPayStatus,Boolean protocolStatus) {
        return iFoOrderService.list(pageable.convert(), deliveryId, userId, driverId, confirmStatus, transportStatus, payStatus, evaluateStatus, driverEvaluateStatus, cancelStatus, refundStatus,  driverPayStatus,protocolStatus);
    }

//    创建订单
    @PostMapping
    public FoOrder foOrder(@Valid @RequestBody FoOrderReq req, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        return iFoOrderService.create(req, myUserDetails,true);
    }

    //取消订单
    @PostMapping("/{id}/cancel")
    public FoOrder cancel(@PathVariable Long id, Authentication authentication,String description) {
        return iFoOrderService.cancel(id, authentication,description);
    }


    @PostMapping("/{id}/refund")
    public FoOrder refund(@PathVariable Long id, Authentication authentication,String description) {
        return iFoOrderService.refund(id, authentication,description);
    }



    @PostMapping("/confirm/{id}")
    @PreAuthorize("isAuthenticated()")
    public FoOrder confirm(@PathVariable Long id , Authentication authentication){
        return  iFoOrderService.confirm(id,authentication);
    }


}
