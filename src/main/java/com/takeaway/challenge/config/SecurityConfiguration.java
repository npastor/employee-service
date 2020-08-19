package com.takeaway.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
               .withUser("user")
               .password("user")
               .roles("USER")
               .and()
               .withUser("admin")
               .password("admin")
               .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/swagger-ui-html")
            .permitAll()
            .antMatchers(HttpMethod.GET)
            .permitAll()
            .antMatchers(HttpMethod.PUT)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.POST)
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT)
            .hasRole("ADMIN")
            .and()
            .csrf()
            .disable()
            .formLogin();
        super.configure(http);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
