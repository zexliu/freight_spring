package wiki.zex.cloud.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wiki.zex.cloud.example.entity.SyUser;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.SyUserReq;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.resp.SyUserResp;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-05-12
 */
public interface ISyUserService extends IService<SyUser> {

    SyUser findByUsername(String username);

    SyUser create(SyUserReq req);

    SyUser update(Long id, SyUserReq req);

    void delete(Long id);

    void password(Long id,String password);

    SyUserResp getDetailsById(Long id);

    IPage<SyUser> list(Page<SyUser> convert, String username, String mobile, String realName, String workNo, Long deptId, Boolean enable, Boolean locked);
}
