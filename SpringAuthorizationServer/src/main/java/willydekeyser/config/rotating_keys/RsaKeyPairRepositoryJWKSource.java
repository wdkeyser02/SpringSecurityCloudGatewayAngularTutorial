package willydekeyser.config.rotating_keys;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import willydekeyser.config.rotating_keys.RsaKeyPairRepository.RsaKeyPair;

@Component
public class RsaKeyPairRepositoryJWKSource implements JWKSource<SecurityContext>, OAuth2TokenCustomizer<JwtEncodingContext> {

	private final RsaKeyPairRepository keyPairRepository;

	public RsaKeyPairRepositoryJWKSource(RsaKeyPairRepository keyPairRepository) {
		this.keyPairRepository = keyPairRepository;
	}
	
	@Override
	public void customize(JwtEncodingContext context) {
		Authentication principal = context.getPrincipal();
		if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
			Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toSet());
			context.getClaims().claim("authorities", authorities);
		}
		
		if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
			Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toSet());
			context.getClaims().claim("authorities", authorities);
			context.getClaims().claim("details", "Spring Boot Tutorial");
		}
		
		List<RsaKeyPair> keyPairs = this.keyPairRepository.findKeyPairs();
		String kid = keyPairs.get(0).id();
		context.getJwsHeader().keyId(kid);
		
	}

	@Override
	public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
		List<RsaKeyPair> keyPairs = this.keyPairRepository.findKeyPairs();
		List<JWK> result = new ArrayList<>(keyPairs.size());
		for (RsaKeyPair keyPair : keyPairs) {

			RSAKey rsaKey = new RSAKey.Builder(keyPair.publicKey())
					.privateKey(keyPair.privateKey())
					.keyID(keyPair.id())
					.build();
			if (jwkSelector.getMatcher().matches(rsaKey)) {
				result.add(rsaKey);
			}
		}
		return result;
	}

}
