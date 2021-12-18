package com.example.jdbl.libraryapp;

import com.example.jdbl.libraryapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LibraryConfig extends WebSecurityConfigurerAdapter {
    //USER_ROLE
    //ADMIN_ROLE (Librarian)

    @Autowired
    UserService userService;

    @Value("${app.security.admin_role}")
    private String ADMIN_ROLE;

    @Value("${app.security.student_role}")
    private String STUDENT_ROLE;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/book/**").hasAnyAuthority(ADMIN_ROLE,STUDENT_ROLE)
                .antMatchers("/book/**").hasAnyAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST,"/student/**").permitAll()
                .antMatchers(HttpMethod.GET,"/student/id/**").hasAuthority(ADMIN_ROLE)
                .antMatchers("/student/**").hasAuthority(STUDENT_ROLE)
                .antMatchers(HttpMethod.GET,"/transaction/fine/student_id/**").hasAuthority(ADMIN_ROLE)
                .antMatchers("transaction/**").hasAuthority(STUDENT_ROLE)
                .antMatchers("/admin/**").hasAuthority(ADMIN_ROLE)
                .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }


}
