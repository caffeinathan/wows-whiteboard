package org.comit.nrogowski.wows_whiteboard.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import jakarta.servlet.http.HttpServletRequest;

public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

	private OAuth2AuthorizationRequestResolver defaultResolver;
	public CustomAuthorizationRequestResolver(ClientRegistrationRepository repo, String authRequestBaseUri) {
		defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, authRequestBaseUri);
	}
	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
		OAuth2AuthorizationRequest authRequest = defaultResolver.resolve(request);
		if (authRequest != null) {
			authRequest = customizeAuthRequest(authRequest);
		}
		return authRequest;
	}
	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
		OAuth2AuthorizationRequest authRequest = defaultResolver.resolve(request, clientRegistrationId);
		if (authRequest != null) {
			authRequest = customizeAuthRequest(authRequest);
		}
		return authRequest;
	}
	
	private OAuth2AuthorizationRequest customizeAuthRequest(OAuth2AuthorizationRequest authRequest) {
		return null; //TODO
	}

}
