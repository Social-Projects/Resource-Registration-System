package org.registrator.community.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.inMemoryAuthentication()
        .withUser("registrator").password("registrator").roles("REGISTRATOR")
      .and()
	    .withUser("admin").password("admin").roles("ADMIN")
      .and()
	    .withUser("user").password("user").roles("USER");
	}

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().loginPage("/login").permitAll()

                .failureUrl("/login?error")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/login?logout").and().exceptionHandling().accessDeniedPage("/login").and()

                .authorizeRequests()
                .antMatchers("/administrator/**").hasRole("ADMIN")
                .antMatchers("/registrator/**").hasRole("REGISTRATOR")
                .antMatchers("/inquiry/**").hasRole("USER").and()
                .csrf().disable();


    }


}