package com.hsjnb.yd_ecommerce_platform_api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.LoginDto;
import com.hsjnb.yd_ecommerce_platform_api.utils.JwtUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.RedisUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
 * @date : Created in 2022/03/16 21:45
 * @description : 登录拦截
 */

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        // 验证码判断
        String url = request.getRequestURI();
        LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);// 拿到登录信息
        if ("/login".equals(url) && request.getMethod().equals("POST")) {
            Result result = validateCode(loginDto.getKey(), loginDto.getCaptcha());
            if (!ObjectUtils.isEmpty(result)) {
                response.setContentType(Constant.CONTENT_TYPE);
                PrintWriter writer = response.getWriter();
                writer.write(new ObjectMapper().writeValueAsString(result));
                writer.flush();
                writer.close();
                return null;
            }
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    /**
     * 登录成功之后的操作
     * @param request request
     * @param response response
     * @param chain chain
     * @param authResult authResult
     * @throws IOException IOException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        if (jwtUtil == null) {
            jwtUtil = SpringContextHolder.getBean(JwtUtil.class);
        }
        // 生成token
        String token = jwtUtil.generateToken(authResult);
        response.setHeader(jwtUtil.getHeader(),token);// 将token设置到header
        response.setContentType(Constant.CONTENT_TYPE);
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(Result.success(200,"登录成功", token)));
        writer.flush();
        writer.close();
    }

    /**
     * 登录失败之后的操作
     * @param request request
     * @param response response
     * @param failed failed
     * @throws IOException IOException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(Constant.CONTENT_TYPE);
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(Result.fail(400,failed.getMessage(),null)));
        writer.flush();
        writer.close();
    }

    /**
     * 验证码判断处理
     * @param key key
     * @param captcha captcha
     * @return Result
     */
    private Result validateCode(String key, String captcha) {
        if (redisUtil == null) {
            redisUtil = SpringContextHolder.getBean(RedisUtil.class);
        }
        if (!StringUtils.hasText(key) || !StringUtils.hasText(captcha)) {
            return Result.fail(400,"验证码错误",null);
        }
        if (ObjectUtils.isEmpty(redisUtil.get(key))) {
            return Result.fail(400,"验证码过期",null);
        }
        if (!captcha.equals(redisUtil.get(key))) {
            return Result.fail(400,"验证码错误",null);
        }
        redisUtil.del(key);
        return null;
    }
}
