package willydekeyser.config.rotating_keys;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
class RsaPrivateKeyConverter implements Serializer<RSAPrivateKey>, Deserializer<RSAPrivateKey> {
  
	private final TextEncryptor textEncryptor;

    RsaPrivateKeyConverter(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }
    
    @Override
    public RSAPrivateKey deserialize(InputStream inputStream) {
        try {
            String pem = this.textEncryptor.decrypt(FileCopyUtils.copyToString(new InputStreamReader(inputStream)));
            String privateKeyPEM = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");
            byte[] encoded = Base64.getMimeDecoder().decode(privateKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException("There's been an exception", throwable);
        }
    }

    @Override
    public void serialize(RSAPrivateKey key, OutputStream outputStream) throws IOException {
    	PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key.getEncoded());
        String string = "-----BEGIN PRIVATE KEY-----\n" + Base64.getMimeEncoder().encodeToString(pkcs8EncodedKeySpec.getEncoded())
                     + "\n-----END PRIVATE KEY-----";
        outputStream.write(this.textEncryptor.encrypt(string).getBytes());
    }
}