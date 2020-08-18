package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.FoDeliveryExtension;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.security.MyUserDetails;
import wiki.zex.cloud.example.service.IFoDeliveryExtensionService;

/**
 * <p>
 * 用户发货 拓展信息 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-07-02
 */
@RestController
@RequestMapping("/api/v1/delivery/extension")
public class FoDeliveryExtensionController {

    @Autowired
    private IFoDeliveryExtensionService iFoDeliveryExtensionService;


}
