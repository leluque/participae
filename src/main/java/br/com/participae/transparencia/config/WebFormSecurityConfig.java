package br.com.participae.transparencia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebFormSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    /*.antMatchers("/admin/resellers")
                        .hasAnyRole("ROLE_ADMIN", "ROLE_RESELLER")
                    .antMatchers("/admin/representatives", "/admin/passwordLots")
                        .hasAnyRole("ROLE_ADMIN", "ROLE_RESELLER", "ROLE_REPRESENTATIVES")*/
                    .antMatchers("/assets/**", "/admin/login", "/**")
                        .permitAll()
                        .anyRequest().authenticated()
                        .and()
                    .formLogin()
                        .loginPage("/admin/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/admin/login?error=1")
                        .permitAll()
                        .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                        .logoutSuccessUrl("/admin/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll();
/*                        .and()
                        .csrf()
                        .ignoringAntMatchers("/admin/address/**");*/
	}

}
