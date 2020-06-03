package wiki.zex.cloud.example.security;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wiki.zex.cloud.example.entity.SyPermission;
import wiki.zex.cloud.example.entity.SyUser;
//import wiki.zex.cloud.example.resp.SyRoleResp;
import wiki.zex.cloud.example.resp.SyRoleResp;
import wiki.zex.cloud.example.service.ISyPermissionService;
import wiki.zex.cloud.example.service.ISyRoleService;
import wiki.zex.cloud.example.service.ISyUserService;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ISyUserService iSyUserService;

    @Autowired
    private ISyRoleService iSyRoleService;


    @Autowired
    private ISyPermissionService iSyPermissionService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SyUser syUser = iSyUserService.findByUsername(username);
        if (syUser == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        List<SyRoleResp> roles =  iSyRoleService.findRolesByUserId(syUser.getId());
        MyUserDetails myUserDetails = new MyUserDetails();
        BeanUtils.copyProperties(syUser, myUserDetails);
        for (SyRoleResp role : roles) {
            List<SyPermission> permissions = iSyPermissionService.getByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        myUserDetails.setRoles(roles);
        return myUserDetails;
    }
}
