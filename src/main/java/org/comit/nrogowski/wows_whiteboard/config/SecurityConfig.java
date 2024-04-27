package org.comit.nrogowski.wows_whiteboard.config;

import org.comit.nrogowski.wows_whiteboard.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) {
//		http.oauth2Login()
//			.authorizationEndpoint()
//			.authorizationRequestResolver(new CustomAuthorizationRequestResolver(
//					clientRegistrationRepository(), Constants.AUTH_REQ_BASE_URI));
//	}
}
