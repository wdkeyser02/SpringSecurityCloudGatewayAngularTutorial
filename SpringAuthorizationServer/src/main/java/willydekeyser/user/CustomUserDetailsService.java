package willydekeyser.user;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;


public class CustomUserDetailsService implements UserDetailsService {

	private final JdbcTemplate jdbcTemplate;
	private final BytesEncryptor bytesEncryptor;
	
	public CustomUserDetailsService(JdbcTemplate jdbcTemplate, BytesEncryptor bytesEncryptor) {
		this.jdbcTemplate = jdbcTemplate;
		this.bytesEncryptor = bytesEncryptor;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<>();
		String sql = """
				SELECT user.username, user.password, user.enabled, authorities.authority, userinfo.isAccountNonExpired, 
				userinfo.isAccountNonLocked, userinfo.isCredentialsNonExpired, userinfo.securityQuestion, 
				userinfo.securityAnswer, userinfo.mfaSecret, userinfo.mfaKeyId, userinfo.mfaEnabled, 
				userinfo.mfaRegistered, userinfo.securityQuestionEnabled  
				FROM usersinfo userinfo, users user 
				LEFT JOIN authorities on user.username = authorities.username 
				WHERE user.username = userinfo.username AND user.username = ?;
				""";
		return jdbcTemplate.query(sql, rs -> {
			String username = "";
			String password = "";
			boolean enabled = false;
			boolean isAccountNonExpired = false;
			boolean isAccountNonLocked = false;
			boolean isCredentialsNonExpired = false;
			String securityQuestion = "";
			String securityAnswer = "";
			String mfaSecret = "";
			String mfaKeyId = "";
			boolean mfaEnabled = false;
			boolean mfaRegistered = false;
			boolean securityQuestionEnabled = false;
			boolean first = true;
			while (rs.next()) {
				if (first) {
					first = false;
					username = rs.getString("username");
					password = rs.getString("password");
					enabled = rs.getBoolean("enabled");
					isAccountNonExpired = rs.getBoolean("isAccountNonExpired");
					isAccountNonLocked = rs.getBoolean("isAccountNonLocked");
					isCredentialsNonExpired = rs.getBoolean("isCredentialsNonExpired");
					securityQuestion = rs.getString("securityQuestion");
					securityAnswer = rs.getString("securityAnswer");
					mfaSecret = rs.getString("mfaSecret");
					mfaKeyId = rs.getString("mfaKeyId");
					mfaEnabled = rs.getBoolean("mfaEnabled");
					mfaRegistered = rs.getBoolean("mfaRegistered");
					securityQuestionEnabled = rs.getBoolean("securityQuestionEnabled");
				}
				
				authorities.add(new SimpleGrantedAuthority(rs.getString("authority")));
			}
			if (userName.equals(username)) {
				return new User(username, password, enabled, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, authorities,
						securityQuestion, securityAnswer, mfaSecret, mfaKeyId, mfaEnabled, mfaRegistered, securityQuestionEnabled);
			}
			throw new UsernameNotFoundException("User not found!");
		}, userName);

	}

	public void saveUserInfoMfaRegistered(String secret, String username) {
		String encryptedSecret = new String(Hex.encode(this.bytesEncryptor.encrypt(secret.getBytes(StandardCharsets.UTF_8))));
		String sql = """
				UPDATE usersinfo SET mfaSecret = ?, mfaRegistered = true WHERE username = ?;
				""";
		this.jdbcTemplate.update(sql, encryptedSecret, username);
	}
	
}
