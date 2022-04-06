package com.hsjnb.yd_ecommerce_platform_api.config;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
 * @date : Created in 2022/03/15 15:11
 * @description :
 */

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LogoutHandler logoutHandler;

    private final ApplicationContext applicationContext;

    @Autowired
    public SecurityConfig(LogoutHandler logoutHandler, ApplicationContext applicationContext) {
        this.logoutHandler = logoutHandler;
        this.applicationContext = applicationContext;
    }

    // 白名单
    public static final String[] URL_WHITELIST = {
            "/login",
            "/app/login"
    };

    @Bean
    LoginFilter loginFilter() throws Exception {
        return new LoginFilter("/**/login", authenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
            // 获取所有接口的方法
            HandlerMethod handlerMethod = infoEntry.getValue();
            // 判断是否包含 AnonymousAccess注解
            if ( handlerMethod.hasMethodAnnotation(AnonymousAccess.class)) {
                final PatternsRequestCondition requestCondition = infoEntry.getKey().getPatternsCondition();
                Optional.ofNullable(requestCondition).orElseThrow(RuntimeException::new);
                // 将允许匿名访问的url添加到匿名访问列表中
                anonymousUrls.addAll(requestCondition.getPatterns());
            }
        }
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
                .antMatchers(URL_WHITELIST).permitAll()//白名单
                // 静态资源等等
                .antMatchers(
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/v2/api-docs-ext").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/api/canvas/**").permitAll()

                .anyRequest().authenticated()

                .and()
                .logout()
                .logoutUrl("/**/logout")
                .logoutSuccessHandler(logoutHandler)
                .and()
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager()));
    }

}
