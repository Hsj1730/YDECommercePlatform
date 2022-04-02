package com.hsjnb.yd_ecommerce_platform_api.service;

import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.AppUserMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Hsj1730
 * @version : 1.0
 * @date : Created in 2022/03/17 2:10
 * @description : 获取用户和权限信息
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final AppUserMapper appUserMapper;

    private final HttpServletRequest request;

    @Autowired
    public UserDetailServiceImpl(UserMapper userMapper,AppUserMapper appUserMapper,HttpServletRequest request) {
        this.userMapper = userMapper;
        this.appUserMapper = appUserMapper;
        this.request = request;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url = request.getRequestURI();
        if (url.startsWith("/app/")) {
            AppUser appUser = appUserMapper.getUserInfoByAccount(username);
            if (appUser == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            return appUser;
        } else {
            // 获取用户信息
            User user = userMapper.getUserByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            // 获取用户角色
            List<GrantedAuthority> userRoleInfo = getUserRoleInfo(user.getId());
            user.setAuthorities(userRoleInfo);
            return user;
        }

    }

    /**
     * 获取用户的角色编码转成spring security
     * @param id 用户id
     * @return List<GrantedAuthority>
     */
    public List<GrantedAuthority> getUserRoleInfo(Integer id) {
        String authority = "";
        List<String> userRoleCode = userMapper.getUserRoleCode(id);
        if (userRoleCode.size() > 0) {
            String userRole = userRoleCode.stream().map(item -> "ROLE_" + item).collect(Collectors.joining(","));
            authority = userRole.concat(",");
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
