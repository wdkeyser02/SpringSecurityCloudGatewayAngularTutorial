package willydekeyser.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;


@Configuration
public class KeysConfig {

	@Bean
    JWKSource<SecurityContext> jwkSource(
            @Value("${jwt.key.id}") String id,
            @Value("${jwt.key.private}") RSAPrivateKey privateKey,
            @Value("${jwt.key.public}") RSAPublicKey publicKey) {
        var rsa = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(id)
                .build();
        var jwk = new JWKSet(rsa);
        return new ImmutableJWKSet<>(jwk);
    }
}
