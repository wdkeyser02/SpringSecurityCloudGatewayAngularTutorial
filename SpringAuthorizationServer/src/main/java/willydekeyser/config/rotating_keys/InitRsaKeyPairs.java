package willydekeyser.config.rotating_keys;

import java.time.Instant;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import willydekeyser.config.rotating_keys.RsaKeyPairRepository.RsaKeyPair;

@Component
public class InitRsaKeyPairs implements ApplicationRunner {

	private final RsaKeyPairRepository repository;
	private final Keys keys;

	public InitRsaKeyPairs(RsaKeyPairRepository repository, Keys keys) {
		this.repository = repository;
		this.keys = keys;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		RsaKeyPair keypair = keys.generateKeyPair(Instant.now());
		this.repository.save(keypair);
	}
	
}
