package com.its.dsrc.config;

import com.its.dsrc.security.AjaxSessionCheckFilter;
import com.its.dsrc.security.SessionListener;
import com.its.dsrc.security.UserAuthenticationProvider;
import com.its.dsrc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSessionListener;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecuritiyConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private UserAuthenticationProvider authenticationProvider;
    @Autowired
    private UserService userService;


    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("SecurityConfig configure...");

        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/index").hasRole("ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/login");

        http.csrf().disable();



        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login")
                /*.sessionFixation()
                .migrateSession()*/
                .maximumSessions(5)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login")
                .sessionRegistry(sessionRegistry());



    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }// Register HttpSessionEventPublisher
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }


    @Bean
   public HttpSessionListener httpSessionListener(){

        return new SessionListener();

    }
    @Bean
    public AjaxSessionCheckFilter ajaxSessionCheckFilter() throws Exception{
        AjaxSessionCheckFilter ajaxSessionCheckFilter = new AjaxSessionCheckFilter();
        return ajaxSessionCheckFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(authenticationProvider);

        auth.userDetailsService(userService);
    }
}
