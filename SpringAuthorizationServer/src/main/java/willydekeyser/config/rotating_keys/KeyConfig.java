package willydekeyser.config.rotating_keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class KeyConfig {

	@Bean
    TextEncryptor textEncryptor(
            @Value("${jwt.encryptor.password}") String password,
            @Value("${jwt.encryptor.salt}") String salt) {
        return Encryptors.text(password, salt);
    }
	
	@Bean
	NimbusJwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	@Bean
    OAuth2TokenGenerator<OAuth2Token> delegatingOAuth2TokenGenerator(JwtEncoder encoder,
    		OAuth2TokenCustomizer<JwtEncodingContext> customizer) {		
		JwtGenerator generator = new JwtGenerator(encoder);
        generator.setJwtCustomizer(customizer);
        return new DelegatingOAuth2TokenGenerator(generator,
                new OAuth2AccessTokenGenerator(), new OAuth2RefreshTokenGenerator());
    }
	
}
