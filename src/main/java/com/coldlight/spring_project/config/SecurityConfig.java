package com.coldlight.spring_project.config;

import com.coldlight.spring_project.model.RoleStatus;
import com.coldlight.spring_project.security.jwt.JwtConfigurer;
import com.coldlight.spring_project.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring Security configuration class.
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String USER_ENDPOINT = "/api/v1/users/**";
    private static final String ADMIN_ENDPOINT = "/api/v1/admins/**";
    private static final String MODERATOR_ENDPOINT = "/api/v1/moderators/**";
    private static final String FILES_ENDPOINT = "/api/v1/files/**";
    private static final String EVENTS_ENDPOINT = "/api/v1/events/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, USER_ENDPOINT, FILES_ENDPOINT, EVENTS_ENDPOINT).hasRole(String.valueOf(RoleStatus.USER))
                .antMatchers(MODERATOR_ENDPOINT, FILES_ENDPOINT, EVENTS_ENDPOINT).hasRole(String.valueOf(RoleStatus.MODERATOR))
                .anyRequest().hasRole(String.valueOf(RoleStatus.ADMIN))
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
