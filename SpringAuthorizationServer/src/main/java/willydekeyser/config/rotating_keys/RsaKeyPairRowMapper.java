package willydekeyser.config.rotating_keys;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import willydekeyser.config.rotating_keys.RsaKeyPairRepository.RsaKeyPair;

@Component
public class RsaKeyPairRowMapper implements RowMapper<RsaKeyPairRepository.RsaKeyPair> {

	private final RsaPrivateKeyConverter rsaPrivateKeyConverter;
    private final RsaPublicKeyConverter rsaPublicKeyConverter;
    
    RsaKeyPairRowMapper(
    		RsaPrivateKeyConverter rsaPrivateKeyConverter,
            RsaPublicKeyConverter rsaPublicKeyConverter) {
    	this.rsaPrivateKeyConverter = rsaPrivateKeyConverter;
    	this.rsaPublicKeyConverter = rsaPublicKeyConverter;
    }
    
	@Override
	public RsaKeyPair mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			byte[] privateKeyBytes = rs.getString("private_key").getBytes();
			RSAPrivateKey privateKey = this.rsaPrivateKeyConverter.deserializeFromByteArray(privateKeyBytes);
			byte[] publicKeyBytes = rs.getString("public_key").getBytes();
	        RSAPublicKey publicKey = this.rsaPublicKeyConverter.deserializeFromByteArray(publicKeyBytes);		
			Instant created = new Date(rs.getDate("created").getTime()).toInstant();
			return new RsaKeyPair(
					rs.getString("id"), 
					created, 
					publicKey, 
					privateKey);
		}
		catch (Exception  e) {
			throw new RuntimeException(e);
		}
		
	}

}
