package org.cph.crypto.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class JwtConverter extends DefaultAccessTokenConverter {

	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		final OAuth2Authentication auth = super.extractAuthentication(map);
		auth.setDetails(map);
		return auth;
	}
}

