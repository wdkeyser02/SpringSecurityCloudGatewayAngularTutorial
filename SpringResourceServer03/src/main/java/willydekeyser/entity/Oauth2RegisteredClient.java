package willydekeyser.entity;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public record Oauth2RegisteredClient(
		String id, 
		String clientId, 
		String clientSecret, 
		Date clientIdIssuedAt, 
		Date clientSecretExpiresAt, 
		String clientName,
		Set<String> clientAuthenticationMethods,
		Set<String> authorizationGrantTypes,
		Set<String> redirectUris,
		Set<String> postLogoutRedirectUris,
		Set<String> scopes,
		Map<String, Object> clientSettings,
		Map<String, Object> tokenSettings
		) {

}
