package willydekeyser.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import willydekeyser.entity.Key;

@Service
public class KeyService {

	private final JdbcTemplate jdbcTemplate;

	public KeyService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Key> findAllKey() {
		String sql = "SELECT id, created, private_key, public_key FROM rsa_key_pairs order by created desc;";
		List<Key> keyLijst = new ArrayList<>();
		return jdbcTemplate.query(sql, rs -> {
			while (rs.next()) {
				String id = rs.getString("id");
				Instant created =  rs.getTimestamp("created").toInstant();
				String privateKey = rs.getString("private_key");
				String publicKey = rs.getString("public_key");
				keyLijst.add(new Key(id, created, publicKey, privateKey));
			}
			return keyLijst;
		});
	}
}
