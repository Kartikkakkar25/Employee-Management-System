package com.kartik.LoginProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Autowired
	private UserDetailsService userDetailsService;

    @SuppressWarnings("deprecation")
	@Bean
    AuthenticationProvider authprovider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
	}

    @Bean
    SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {

        http
                .csrf(customizer -> customizer.disable())

                .formLogin(httpform -> httpform
                		.loginPage("/login")
                		.loginProcessingUrl("/login")
                		.failureUrl("/login?error=true")
                		.permitAll())
                .authorizeHttpRequests(registry ->{
    				registry.requestMatchers("/login").permitAll()
    						.requestMatchers("/register").permitAll()
    						.requestMatchers("/attenance").hasAnyRole("USER","ADMIN")
    						.requestMatchers("/admin").hasRole("ADMIN")
    						.requestMatchers("/forgot-password","/css/nologin/**","/images/**").permitAll()
    				.anyRequest().authenticated();
                })
                .formLogin(login -> login.defaultSuccessUrl("/home",true).permitAll())
                .logout(logout -> logout
                		.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
			
		return http.build();
	}
}
