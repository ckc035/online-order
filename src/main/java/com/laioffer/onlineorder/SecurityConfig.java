package com.laioffer.onlineorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;



// let Spring create this object
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /// dataSource and pwEncoder info is wired from application config
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin()
                // login success -> main page; Failure -> we need to write the consequence
                .failureForwardUrl("/login?error=true");
        http
                .authorizeRequests()
                // to visit these (have these in url), you need to have authority ROLE_USER
                .antMatchers("/order/*", "/cart", "/checkout").hasAuthority("ROLE_USER")
                // all other request -> no need authorization
                .anyRequest().permitAll();
    }

    // get authentication info (Including ROLE_USER used above) from DB table for matching with login's pw
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                // not using ORM, rather use SQL to query Customers table
                .usersByUsernameQuery("SELECT email, password, enabled FROM customers WHERE email=?")
                .authoritiesByUsernameQuery("SELECT email, authorities FROM authorities WHERE email=?");

    }
}

