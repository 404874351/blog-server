package com.zjc.blog.config;

import com.zjc.blog.security.filter.AuthCodeAuthenticationFilter;
import com.zjc.blog.security.filter.JwtAuthenticationFilter;
import com.zjc.blog.security.filter.NormalAuthenticationFilter;
import com.zjc.blog.security.filter.PermissionAuthorizationFilter;
import com.zjc.blog.security.handler.*;
import com.zjc.blog.security.provider.AuthCodeAuthenticationProvider;
import com.zjc.blog.security.provider.NormalAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 路径权限拦截白名单
     */
    public static final String[] URL_WHITE_LIST = {
            // 登录接口
            "/login/**",

    };

    /**
     * 静态资源白名单
     */
    public static final String[] URL_STATIC_LIST = {
            // 静态资源
            "/*.html",
            "/*.js",
            "/*.css",
    };

    /**
     * 实名访问权限拒绝异常处理器
     */
    @Autowired
    private PermissionAccessDeniedHandler permissionAccessDeniedHandler;
    /**
     * jwt匿名访问认证拒绝处理器
     */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * 登录成功处理器
     */
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    /**
     * 登录失败处理器
     */
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    /**
     * 登出成功处理器
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    /**
     * 权限认证过滤器
     */
    @Autowired
    private PermissionAuthorizationFilter permissionAuthorizationFilter;
    /**
     * 权限访问控制管理器
     */
    @Autowired
    private PermissionAccessDecisionManager permissionAccessDecisionManager;

    /**
     * 自行配置 AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置jwt过滤器
     */
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManagerBean());
    }

    /**
     * 配置密码加密方法
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 普通登录验证器
     */
    @Bean
    NormalAuthenticationProvider normalAuthenticationProvider() {
        return new NormalAuthenticationProvider();
    }

    /**
     * 配置普通登录过滤器
     */
    @Bean
    NormalAuthenticationFilter normalAuthenticationFilter() throws Exception {
        NormalAuthenticationFilter normalAuthenticationFilter = new NormalAuthenticationFilter(authenticationManagerBean());
        normalAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        normalAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return normalAuthenticationFilter;
    }

    /**
     * 验证码登录验证器
     */
    @Bean
    AuthCodeAuthenticationProvider authCodeAuthenticationProvider() {
        return new AuthCodeAuthenticationProvider();
    }

    /**
     * 配置验证码登录过滤器
     */
    @Bean
    AuthCodeAuthenticationFilter authCodeAuthenticationFilter() throws Exception {
        AuthCodeAuthenticationFilter authCodeAuthenticationFilter = new AuthCodeAuthenticationFilter(authenticationManagerBean());
        authCodeAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        authCodeAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return authCodeAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 统一配置登录认证方式
        auth.parentAuthenticationManager(authenticationManagerBean());
        auth.authenticationProvider(normalAuthenticationProvider());
        auth.authenticationProvider(authCodeAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        // 静态资源不走过滤器链
        web.ignoring().antMatchers(URL_STATIC_LIST);
    }

    /**
     * 配置登入登出、异常处理、会话管理等
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭cors和csrf
        http.cors().and().csrf().disable();
        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 配置路径拦截，除白名单外，所有请求都进行jwt认证
        // 但是，白名单依然会走过滤器链，只是不拦截而已
        http.authorizeRequests()
            .antMatchers(URL_WHITE_LIST).permitAll()
            .anyRequest().authenticated();

        // 关闭默认表单登录
        http.formLogin().disable();
        // 对每个provider配置filter处理登录，位置在默认登录认证过滤器之前
        http.addFilterBefore(normalAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 统一的登出配置，登出处理默认在登录认证之前
        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutSuccessHandler);

        // 添加接口权限控制
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                fsi.setSecurityMetadataSource(permissionAuthorizationFilter);
                fsi.setAccessDecisionManager(permissionAccessDecisionManager);
                return fsi;
            }
        });

        // 添加jwt认证和权限拒绝处理器
        http.exceptionHandling()
                .accessDeniedHandler(permissionAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
        // 添加jwt认证过滤器，必须添加在登出和登录认证之前
        http.addFilterBefore(jwtAuthenticationFilter(), LogoutFilter.class);
    }

}
