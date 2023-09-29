package willydekeyser.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import willydekeyser.entity.AuthorizationConsent;

@Service
public class AuthorizationConsentService {

	private final JdbcTemplate jdbcTemplate;

	public AuthorizationConsentService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<AuthorizationConsent> findAllAuthorizationConsent() {
		String sql = "SELECT registered_client_id, principal_name, authorities FROM oauth2_authorization_consent;";
		List<AuthorizationConsent> authorizationConsentLijst = new ArrayList<>();
		return jdbcTemplate.query(sql, rs -> {
			while (rs.next()) {
				String registeredClientId = rs.getString("registered_client_id");
				String principalName = rs.getString("principal_name");
				String authorities = rs.getString("authorities");
				authorizationConsentLijst.add(new AuthorizationConsent(registeredClientId, principalName, authorities));
			}
			return authorizationConsentLijst;
		});
	}
}
