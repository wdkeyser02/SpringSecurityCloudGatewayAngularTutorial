package willydekeyser.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import willydekeyser.entity.Authorization;

@Service
public class AuthorizationService {

	private final JdbcTemplate jdbcTemplate;

	public AuthorizationService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Authorization> findAllAuthorization() {
		String sql = "SELECT id, registered_client_id, principal_name, authorization_grant_type, authorized_scopes, "
				+ "attributes, state, authorization_code_value, authorization_code_issued_at, authorization_code_expires_at, "
				+ "authorization_code_metadata, access_token_value, access_token_issued_at, access_token_expires_at, "
				+ "access_token_metadata, access_token_type, access_token_scopes, oidc_id_token_value, oidc_id_token_expires_at, oidc_id_token_issued_at, "
				+ "oidc_id_token_metadata, refresh_token_value, refresh_token_issued_at, refresh_token_expires_at, "
				+ "refresh_token_metadata, user_code_value, user_code_issued_at, user_code_expires_at, user_code_metadata, "
				+ "device_code_value, device_code_issued_at, device_code_expires_at, device_code_metadata "
				+ "FROM oauth2_authorization;";
		List<Authorization> authorizationLijst = new ArrayList<>();
		return jdbcTemplate.query(sql, rs -> {
			while (rs.next()) {
				String id = rs.getString("id");
				String registeredClientId = rs.getString("registered_client_id");
				String principalName = rs.getString("principal_name");
				String authorizationGrantType = rs.getString("authorization_grant_type");
				String authorizedScopes = rs.getString("authorized_scopes");
				String attributes = rs.getString("attributes");
				String state = rs.getString("state");
				String authorizationCodeValue = rs.getString("authorization_code_value");
				Date authorizationCodeIssuedAt = rs.getTimestamp("authorization_code_issued_at");
				Date authorizationCodeExpiresAt = rs.getTimestamp("authorization_code_expires_at");
				String authorizationCodeMetadata = rs.getString("authorization_code_metadata");
				String accessTokenValue = rs.getString("access_token_value");
				Date accessTokenIssuedAt = rs.getTimestamp("access_token_issued_at");
				Date accessTokenExpiresAt = rs.getTimestamp("access_token_expires_at");
				String accessTokenMetadata = rs.getString("access_token_metadata");
				String accessTokenType = rs.getString("access_token_type");
				String accessTokenScopes = rs.getString("access_token_scopes");
				String oidcIdTokenValue = rs.getString("oidc_id_token_value");
				Date oidcIdTokenExpiresAt = rs.getTimestamp("oidc_id_token_expires_at");
				Date oidcIdTokenIssuedAt = rs.getTimestamp("oidc_id_token_issued_at");
				String oidcIdTokenMetadata = rs.getString("oidc_id_token_metadata");
				String refreshTokenValue = rs.getString("refresh_token_value");
				Date refreshTokenIssuedAt = rs.getTimestamp("refresh_token_issued_at");
				Date refreshTokenExpiresAt = rs.getTimestamp("refresh_token_expires_at");
				String refreshTokenMetadata = rs.getString("refresh_token_metadata");
				String userCodeValue = rs.getString("user_code_value");
				Date userCodeIssuedAt = rs.getTimestamp("user_code_issued_at");
				Date userCodeExpiresAt = rs.getTimestamp("user_code_expires_at");
				String userCodeMetadata = rs.getString("user_code_metadata");
				String deviceCodeValue = rs.getString("device_code_value");
				Date deviceCodeIssuedAt = rs.getTimestamp("device_code_issued_at");
				Date deviceCodeExpiresAt = rs.getTimestamp("device_code_expires_at");
				String deviceCodeMetadata = rs.getString("device_code_metadata");
				authorizationLijst.add(new Authorization(id, registeredClientId, principalName, authorizationGrantType, authorizedScopes, 
						attributes, state, authorizationCodeValue, authorizationCodeIssuedAt, authorizationCodeExpiresAt, authorizationCodeMetadata, 
						accessTokenValue, accessTokenIssuedAt, accessTokenExpiresAt, accessTokenMetadata, accessTokenType, accessTokenScopes, oidcIdTokenValue, 
						oidcIdTokenExpiresAt, oidcIdTokenIssuedAt, oidcIdTokenMetadata, refreshTokenValue, refreshTokenIssuedAt, refreshTokenExpiresAt, refreshTokenMetadata, userCodeValue, userCodeIssuedAt, userCodeExpiresAt, 
						userCodeMetadata, deviceCodeValue, deviceCodeIssuedAt, deviceCodeExpiresAt, deviceCodeMetadata));
			}
			return authorizationLijst;
		});
	}
}
