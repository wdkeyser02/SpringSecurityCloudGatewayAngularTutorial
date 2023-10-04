package willydekeyser.config.rotating_keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.config.rotating_keys.RsaKeyPairRepository.RsaKeyPair;

@RestController
public class KeyController {

	private final RsaKeyPairRepository repository;

	public KeyController(RsaKeyPairRepository repository) {
		this.repository = repository;
	}


	@GetMapping("/oauth2/new_jwks")
	String generate() {
		RsaKeyPair keypair = generateKeyPair(Instant.now());
		this.repository.save(keypair);
		return keypair.id();
	}

	private static RsaKeyPair generateKeyPair(Instant created) {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return new RsaKeyPair(UUID.randomUUID().toString(), created, publicKey, privateKey);
	}

	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}
}
