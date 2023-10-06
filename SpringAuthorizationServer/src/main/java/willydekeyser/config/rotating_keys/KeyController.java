package willydekeyser.config.rotating_keys;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.config.rotating_keys.RsaKeyPairRepository.RsaKeyPair;

@RestController
public class KeyController {

	private final RsaKeyPairRepository repository;
	private final Keys keys;

	public KeyController(RsaKeyPairRepository repository, Keys keys) {
		this.repository = repository;
		this.keys = keys;
	}

	@GetMapping("/oauth2/new_jwks")
	String generate() {
		RsaKeyPair keypair = keys.generateKeyPair(Instant.now());
		this.repository.save(keypair);
		return keypair.id();
	}

}
