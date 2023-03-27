package com.example.rungroup.security;

import javax.servlet.FilterChain;

import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private CustomUserDetailsService userDetailsSrv;
    // private BCryptPasswordEncoder passwEncoder;

    @Bean
    public static BCryptPasswordEncoder passwEncoder(){
        return new BCryptPasswordEncoder();
    };




    @Bean
    public SecurityFilterChain FilterChain (HttpSecurity http) throws Exception{

        http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/login", "/auth/register", "*/css/**", "*/js/**")
        .permitAll()
        .and()
        .formLogin(form -> form.loginPage("/auth/login")
            .defaultSuccessUrl("/clubs")
            .loginProcessingUrl("/auth/login")
            .failureUrl("/auth/login?error=true")
            .permitAll())
        .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")).permitAll());

                    
        return http.build();
    }
    
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsSrv).passwordEncoder(passwEncoder());
    }
}
