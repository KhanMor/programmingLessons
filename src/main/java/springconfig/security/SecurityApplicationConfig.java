package springconfig.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import springconfig.security.service.CustomAccessDeniedHandler;
import springconfig.security.service.CustomUserDetailsService;

/**
 * Created by Mordr on 13.03.2017.
 * Конфигурация security
 */
@Configuration
@EnableWebSecurity
@ComponentScan("springconfig.security.service")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityApplicationConfig extends WebSecurityConfigurerAdapter {
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public void setAccessDeniedHandler(CustomAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder initPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureAuthenticationManager(
            AuthenticationManagerBuilder authenticationManagerBuilder,
            CustomUserDetailsService customUserDetailsService
    ) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(initPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.csrf()
                .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
            .authorizeRequests()
                .mvcMatchers("/css/**","/js/**","/webjars/**","/registration","/registration.success", "/error")
                .permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }
}
