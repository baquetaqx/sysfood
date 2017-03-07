package com.sysfood.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());

		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);

		http.csrf().disable().headers().frameOptions().sameOrigin().and()

				.authorizeRequests().antMatchers("/login.xhtml", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
				.antMatchers("/index.xhtml", "/AcessoNegado.xhtml", "/caixa/**", "/pedido/**", "/produtos/**")
				.authenticated().antMatchers("/usuario/**", "/empresa/**").hasRole("ADMINISTRADOR").and()

				.formLogin().loginPage("/login.xhtml").failureUrl("/login.xhtml?invalid=true").and()

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()

				.exceptionHandling().accessDeniedPage("/AcessoNegado.xhtml").authenticationEntryPoint(jsfLoginEntry)
				.accessDeniedHandler(jsfDeniedEntry);
	}

}