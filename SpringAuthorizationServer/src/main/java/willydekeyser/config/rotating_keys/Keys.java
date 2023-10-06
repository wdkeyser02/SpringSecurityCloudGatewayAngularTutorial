package willydekeyser.config.rotating_keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Keys {

	RsaKeyPairRepository.RsaKeyPair generateKeyPair(Instant created) {
        var keyPair = generateRsaKey();
        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RsaKeyPairRepository.RsaKeyPair(UUID.randomUUID().toString(), created, publicKey, privateKey);
    }

    private KeyPair generateRsaKey() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        }//
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
