package com.fitmate.backend.config;

import com.fitmate.backend.jwt.JwtAccessDeniedHandler;
import com.fitmate.backend.jwt.JwtAuthenticationEntryPoint;
import com.fitmate.backend.jwt.JwtSecurityConfig;
import com.fitmate.backend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)          // pre Authorize method 단위로 사용
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig => configure");
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // 세션사용 x


                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/**" ).permitAll()
//                .antMatchers("/api/v1/member" ).hasAnyRole("MEMBER","STYLIST","ADMIN")
//                .antMatchers("/api/v1/members" ).hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/account/**" ).hasAnyRole("STYLIST")
//                .antMatchers("/api/v1/accountList" ).hasAnyRole("STYLIST")
                .antMatchers("/auth/**" ).permitAll()
                .antMatchers("/ws/**" ).permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(passwordEncoder().encode("root"))
                .authorities("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-ui.*/**")
                .antMatchers("/api-docs/**")
                ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
