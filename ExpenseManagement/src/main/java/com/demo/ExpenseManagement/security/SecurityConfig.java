package com.demo.ExpenseManagement.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> 
            requests.requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
        );
        http.formLogin().defaultSuccessUrl("/expenses/all");
        http.httpBasic();
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        http.csrf().disable(); // Disable CSRF for H2 console access
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        if (!userDetailsManager.userExists("user1")) {
            UserDetails user1 = User.withUsername("user1")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();
            userDetailsManager.createUser(user1);
        }

        if (!userDetailsManager.userExists("admin")) {
            UserDetails admin = User.withUsername("admin")
                    .password(passwordEncoder().encode("admin"))
                    .roles("ADMIN")
                    .build();
            userDetailsManager.createUser(admin);
        }

        return userDetailsManager;
    }
}
