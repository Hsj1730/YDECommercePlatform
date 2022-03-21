package com.hsjnb.yd_ecommerce_platform_api.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Result;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import com.hsjnb.yd_ecommerce_platform_api.mapper.UserMapper;
import com.hsjnb.yd_ecommerce_platform_api.utils.JwtUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.SpringContextHolder;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
 * @date : Created in 2022/03/16 22:15
 * @description : Token 验证授权
 */

public class AuthenticationFilter extends BasicAuthenticationFilter {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserMapper userMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (jwtUtil == null) {
            jwtUtil = SpringContextHolder.getBean(JwtUtil.class);
        }

        if (userMapper == null) {
            userMapper = SpringContextHolder.getBean(UserMapper.class);
        }

        // 得到token
        String token = request.getHeader(jwtUtil.getHeader());
        // 判断 token是否为空
        if (StrUtil.isBlankOrUndefined(token)) {
            chain.doFilter(request,response);
            return;
        }

        // 解析token
        Claims claim = jwtUtil.getClaimByToken(token);
        if (claim == null) {
            Result result = Result.fail(401, "登录异常，请重新登录", null);
            response.setContentType(Constant.CONTENT_TYPE);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
            return;
        }
        if (jwtUtil.isTokenExpired(claim)) {
            Result result = Result.fail(401, "登录过期，请重新登录", null);
            response.setContentType(Constant.CONTENT_TYPE);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
            return;
        }
        StringBuffer authority = new StringBuffer();
        // 拿到用户名
        String username = claim.getSubject();
        // 获取用户信息
        User userByUsername = userMapper.getUserByUsername(username);
        // 获取权限信息
        for (Map map : (List<Map>) claim.get(jwtUtil.getHeader())) {
            authority.append(map.get("authority")).append(",");
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(authority));
        // 将用户信息写入到 Authentication 中， 方便获取
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userByUsername, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }
}
