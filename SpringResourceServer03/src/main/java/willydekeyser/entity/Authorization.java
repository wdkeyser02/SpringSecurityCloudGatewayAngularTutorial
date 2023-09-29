package willydekeyser.entity;

import java.util.Date;

public record Authorization(
		String id,
		String registeredClientId,
		String principal_name,
		String authorizationGrantType,
		String authorizedScopes,
		String attributes,
		String state,
	    String authorizationCodeValue,
	    Date authorizationCodeIssuedAt,
	    Date authorizationCodeExpiresAt,
	    String authorizationCodeMetadata,
	    String accessTokenValue,
	    Date accessTokenIssuedAt,
	    Date accessTokenExpiresAt,
	    String accessTokenMetadata,
	    String accessTokenType,
	    String accessTokenScopes,
	    String oidcIdTokenValue,
	    Date oidcIdTokenIssuedAt,
	    Date oidcIdTokenExpiresAt,
	    String oidcIdTokenMetadata,
	    String refreshTokenValue,
	    Date refreshTokenIssuedAt,
	    Date refreshTokenExpiresAt,
	    String refreshTokenMetadata,
	    String userCodeValue,
	    Date userCodeIssuedAt,
	    Date userCodeExpiresAt,
	    String userCodeMetadata,
	    String deviceCodeValue,
	    Date deviceCodeIssuedAt,
	    Date deviceCodeExpiresAt,
	    String deviceCodeMetadata
		) {

}
