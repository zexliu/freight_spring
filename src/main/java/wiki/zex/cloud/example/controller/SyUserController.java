package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wiki.zex.cloud.example.entity.SyUser;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.req.SyUserReq;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.resp.SyUserResp;
import wiki.zex.cloud.example.service.ISyUserService;
import javax.validation.Valid;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-05-12
 */
@Validated
@RestController
@RequestMapping("/api/v1/users")
@Api(tags = "用户相关接口")
public class SyUserController {

    @Autowired
    private ISyUserService iSyUserService;


    @GetMapping
    @ApiOperation("分页查询")
    IPage<SyUser> list(Pageable pageable, String username, String mobile, String realName, String workNo,Long deptId, Boolean enable, Boolean locked) {
        return iSyUserService.list(pageable.convert(),username,mobile,realName,workNo,deptId,enable,locked);
//        return iSyUserService.page(pageable.convert(), new LambdaQueryWrapper<SyUser>()
//                .like(StringUtils.isNotBlank(username), SyUser::getUsername, username)
//                .like(StringUtils.isNotBlank(mobile), SyUser::getMobile, mobile)
//                .eq(StringUtils.isNotBlank(realName), SyUser::getRealName, realName)
//                .like(StringUtils.isNotBlank(workNo), SyUser::getWorkNo, workNo)
//                .eq(enable != null, SyUser::getEnable, enable)
//                .eq(locked != null, SyUser::getLocked, locked)
//                .orderByDesc(SyUser::getCreateAt)
//        );
    }

    @GetMapping("/{id}")
    public SyUserResp getById(@PathVariable Long id) {
        return iSyUserService.getDetailsById(id);
    }

    @PostMapping
    public SyUser create(@Valid @RequestBody SyUserReq req) {
        return iSyUserService.create(req);
    }

    @PutMapping("/{id}")
    public SyUser update(@PathVariable Long id, @Valid @RequestBody SyUserReq req) {
        return iSyUserService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public SimpleResp delete(@PathVariable Long id) {
        iSyUserService.delete(id);
        return SimpleResp.SUCCESS;
    }

    @GetMapping("/self")
    @PreAuthorize("isAuthenticated()")
    public Object authentication(Authentication authentication) {
        return authentication.getPrincipal();
    }


    @PostMapping("/{id}/password")
    public SimpleResp password(@PathVariable Long id, @RequestParam @Length(min = 32, max = 32)
        String password) {
        iSyUserService.password(id,password);
        return SimpleResp.SUCCESS;
    }

}
