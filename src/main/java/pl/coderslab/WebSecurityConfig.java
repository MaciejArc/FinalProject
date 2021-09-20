package pl.coderslab;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImp userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                .antMatchers("/user/start").hasRole("CLIENT")
                .antMatchers("/faultOrder/add").hasRole("CLIENT")
                .antMatchers("/addresses/**").hasRole("CLIENT")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/faultOrder/all").hasRole("ADMIN")
                .antMatchers("/company/**").hasRole("ADMIN")
                .antMatchers("/faultOrder/update").hasRole("ADMIN")
                .and()
                .formLogin().defaultSuccessUrl("/user/role");
    }
}
