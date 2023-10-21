package willydekeyser.service;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;


@Service
public class AuthenticatorService {

	private final BytesEncryptor bytesEncryptor;

    public AuthenticatorService(BytesEncryptor bytesEncryptor) {
        this.bytesEncryptor = bytesEncryptor;
    }
        
    public boolean check(String key, String code) {
        try {
        	String secret = new String(this.bytesEncryptor.decrypt(Hex.decode(key)), StandardCharsets.UTF_8);
            return TimeBasedOneTimePasswordUtil.validateCurrentNumber(secret, Integer.parseInt(code), 10000);
        }
        catch (IllegalArgumentException ex) {
            return false;
        }
        catch (GeneralSecurityException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    public String generateSecret() {
        return TimeBasedOneTimePasswordUtil.generateBase32Secret();
    }
    
    public String generateQrImageUrl(String keyId, String base32Secret) {
        return TimeBasedOneTimePasswordUtil.qrImageUrl(keyId, base32Secret);
    }
    
    public String getCode(String base32Secret) throws GeneralSecurityException {
    	return TimeBasedOneTimePasswordUtil.generateCurrentNumberString(base32Secret);
    }
}