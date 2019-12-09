package com.pritamprasad.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${root.user}")
    private String rootUser;

    @Value("${root.pass}")
    private String rootUserPass;

    private String rootUserRole="ROOT";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/users/**").permitAll()
                .antMatchers(HttpMethod.POST,"/token").permitAll()
                .antMatchers(HttpMethod.GET,"/validate/**").permitAll()
                .antMatchers(HttpMethod.POST,"/users").hasRole(rootUserRole)
                .antMatchers(HttpMethod.PUT,"/users").hasRole(rootUserRole)
                .antMatchers(HttpMethod.DELETE,"/users").hasRole(rootUserRole)
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(rootUser).password("{noop}"+rootUserPass).roles(rootUserRole);
    }
}
