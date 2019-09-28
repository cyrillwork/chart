package com.cyrillwork.chart.config;

import com.cyrillwork.chart.controllers.ChartPasswordEncoder;
import com.cyrillwork.chart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private ChartPasswordEncoder chartPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http)
        throws Exception
    {
        http
                .authorizeRequests()
                    .antMatchers("/login*","/index", "/registration", "/activate/*").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .loginProcessingUrl("/login")
                    //.failureUrl("/login.html?error=true")
                .and()
                    .rememberMe()
                .and()
                    .logout()
                    .permitAll()
                .and()
                    .logout().deleteCookies("JSESSIONID")
                .and()
                    .sessionManagement()
                        //.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                        .invalidSessionUrl("/login")
                        .maximumSessions(3)
                        .maxSessionsPreventsLogin(true)
                        .sessionRegistry(sessionRegistry());
    }

    @Bean(name = "sessionRegistry")
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(chartPasswordEncoder);
    }
}
