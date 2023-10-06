package willydekeyser.config.rotating_keys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcRsaKeyRepository implements RsaKeyPairRepository {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<RsaKeyPair> keyPairRowMapper;
	
	private final RsaPublicKeyConverter rsaPublicKeyConverter;
    private final RsaPrivateKeyConverter rsaPrivateKeyConverter;
	
	public JdbcRsaKeyRepository(JdbcTemplate jdbcTemplate, 
			RowMapper<RsaKeyPair> keyPairRowMapper,
			RsaPublicKeyConverter publicKeySerializer,
            RsaPrivateKeyConverter privateKeySerializer) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyPairRowMapper = keyPairRowMapper;
		this.rsaPublicKeyConverter = publicKeySerializer;
        this.rsaPrivateKeyConverter = privateKeySerializer;
	}

	@Override
	public List<RsaKeyPair> findKeyPairs() {
		String sql = "select * from rsa_key_pairs order by created desc";
		return jdbcTemplate.query(sql, this.keyPairRowMapper);
	}

	@Override
	public void delete(String id) {
		String sql = "delete from rsa_key_pairs where id = ?";
		this.jdbcTemplate.update(sql);
	}

	@Override
	public void save(RsaKeyPair rsaKeyPair) {
		String sql = "insert into rsa_key_pairs (id, created, public_key, private_key ) values (?, ?, ?, ?)";
		try (ByteArrayOutputStream privateBaos = new ByteArrayOutputStream(); var publicBaos = new ByteArrayOutputStream()) {
	            this.rsaPrivateKeyConverter.serialize(rsaKeyPair.privateKey(), privateBaos);
	            this.rsaPublicKeyConverter.serialize(rsaKeyPair.publicKey(), publicBaos);
			this.jdbcTemplate.update(sql,
					rsaKeyPair.id(),
					new Date(rsaKeyPair.created().toEpochMilli()),
					publicBaos.toString(),
					privateBaos.toString());
		}
		catch (IOException e) {
            throw new IllegalArgumentException("there's been an exception", e);
        }
		
		
	}

}
