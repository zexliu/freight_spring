package wiki.zex.cloud.example.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wiki.zex.cloud.example.entity.SyUser;
import wiki.zex.cloud.example.mapper.SyUserMapper;
import wiki.zex.cloud.example.req.SyUserReq;
import wiki.zex.cloud.example.resp.SyUserResp;
import wiki.zex.cloud.example.service.IExcelService;
import wiki.zex.cloud.example.service.ISyUserDeptRelService;
import wiki.zex.cloud.example.service.ISyUserRoleRelService;
import wiki.zex.cloud.example.service.ISyUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wiki.zex.cloud.example.utils.ExcelUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static wiki.zex.cloud.example.utils.ExcelUtils.BATCH_COUNT;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-05-12
 */
@Service
@Slf4j
public class SyUserServiceImpl extends ServiceImpl<SyUserMapper, SyUser> implements ISyUserService, IExcelService {


    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ISyUserRoleRelService iSyUserRoleRelService;

    @Autowired
    private ISyUserDeptRelService iSyUserDeptRelService;

    @Override
    public SyUser findByUsername(String username) {
        return getOne(new LambdaQueryWrapper<SyUser>().eq(SyUser::getUsername, username));
    }

    @Override
    @Transactional
    public SyUser create(SyUserReq req) {
//        valid(req, null);
        SyUser syUser = new SyUser();
        BeanUtils.copyProperties(req, syUser);
        if (StringUtils.isNotBlank(req.getPassword())){
            passwordEncoder.encode(req.getPassword());
        }
        save(syUser);

        return syUser;
    }

    @Override
    @Transactional
    public SyUser update(Long id, SyUserReq req) {
        SyUser syUser = new SyUser();
        BeanUtils.copyProperties(req, syUser);
        syUser.setId(id);
        updateById(syUser);
        iSyUserRoleRelService.updateByUserId(syUser.getId(), req.getRoleIds());
        iSyUserDeptRelService.updateByUserId(syUser.getId(), req.getDeptIds());
        return syUser;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        iSyUserDeptRelService.removeByUserId(id);
        iSyUserRoleRelService.removeByUserId(id);
        SyUser syUser = new SyUser();
        syUser.setId(id);
        removeById(syUser);

    }

    @Override
    public void password(Long id, String password) {
        SyUser syUser = new SyUser();
        syUser.setId(id);
        syUser.setPassword(passwordEncoder.encode(password));
        updateById(syUser);
    }

    @Override
    public SyUserResp getDetailsById(Long id) {
        SyUserResp resp = new SyUserResp();
        SyUser syUser = getById(id);
        BeanUtils.copyProperties(syUser, resp);
        List<Long> deptIds = iSyUserDeptRelService.getDeptIdsByUserId(id);
        List<Long> roleIds = iSyUserRoleRelService.getRoleIdsByUserId(id);
        resp.setDeptIds(deptIds);
        resp.setRoleIds(roleIds);
        return resp;
    }

    @Override
    public IPage<SyUser> list(Page<SyUser> page, String username, String mobile, String realName, String workNo, Long deptId, Boolean enable, Boolean locked) {
        return baseMapper.list(page,username,mobile,realName,workNo,deptId,enable,locked);
    }

    @Override
    public SyUser findByMobile(String mobile) {
        return getOne(new LambdaQueryWrapper<SyUser>().eq(SyUser::getMobile, mobile));
    }


    @Override
    public void download(HttpServletResponse response) {
        List<SyUser> list = list();
        ExcelUtils.write(response, list, SyUser.class, "用户信息");
    }


    @Override
    @Transactional
    public void upload(MultipartFile file) {
        ExcelUtils.read(SyUser.class, file, new AnalysisEventListener<SyUser>() {

            private List<SyUser> list = new ArrayList<>();

            @Override
            public void invoke(SyUser data, AnalysisContext context) {
                list.add(data);
                // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
                if (list.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    list.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            private void saveData() {
                log.info("{}条数据，开始存储数据库！", list.size());
                list = list.stream().peek(syUser -> {
                    syUser.setId(null);
                }).collect(Collectors.toList());
                saveBatch(list);
                log.info("存储数据库成功！");
            }
        });

    }


}