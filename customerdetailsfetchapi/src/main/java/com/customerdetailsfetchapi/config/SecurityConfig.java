package com.customerdetailsfetchapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity

public class SecurityConfig {
	
	@Bean
	public InMemoryUserDetailsManager userDetailService() {
		UserDetails user= User.withDefaultPasswordEncoder()
				.username("maybank")
				.password("maybank")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager (user);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
        .authorizeRequests()
            .requestMatchers(HttpMethod.GET, "/api/records/**").hasRole("USER") // Restrict GET to users with the 'USER' role
            .requestMatchers(HttpMethod.PUT, "/api/records/**").hasRole("USER") // Restrict PUT to users with the 'USER' role
            .anyRequest().authenticated() // All other requests must be authenticated
        .and()
        .httpBasic() // Basic authentication
        .and()
        .csrf().disable(); // Disable CSRF for simplicity (you can enable it in a production setting)

    return http.build();
	}
}
