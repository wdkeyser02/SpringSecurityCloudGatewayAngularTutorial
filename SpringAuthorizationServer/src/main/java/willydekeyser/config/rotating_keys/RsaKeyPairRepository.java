package willydekeyser.config.rotating_keys;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.List;

public interface RsaKeyPairRepository {

	List<RsaKeyPair> findKeyPairs();

	void delete(String id);

	void save(RsaKeyPair rsaKeyPair);
	
	record RsaKeyPair(String id, Instant created, RSAPublicKey publicKey, RSAPrivateKey privateKey) {}
}
