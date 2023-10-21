package willydekeyser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import willydekeyser.entity.User;

@Service
public class UsersService {
	
	private final JdbcTemplate jdbcTemplate;
	
	public UsersService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<User> findAllUsers() {
		var sql = """
				SELECT user.username, user.password, user.enabled, authorities.authority, userinfo.isAccountNonExpired, 
				userinfo.isAccountNonLocked, userinfo.isCredentialsNonExpired, userinfo.securityQuestion, 
				userinfo.securityAnswer, userinfo.mfaSecret, userinfo.mfaKeyId, userinfo.mfaEnabled, 
				userinfo.mfaRegistered, userinfo.securityQuestionEnabled  
				FROM usersinfo userinfo, users user 
				LEFT JOIN authorities on user.username = authorities.username 
				WHERE user.username = userinfo.username;
				""";
		List<User> usersLijst = new ArrayList<>();
		return jdbcTemplate.query(sql, rs -> {
			Map<String, User> usersByUsername = new HashMap<>();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				boolean enabled = rs.getBoolean("enabled");
				boolean isAccountNonExpired = rs.getBoolean("isAccountNonExpired");
				boolean isAccountNonLocked = rs.getBoolean("isAccountNonLocked");
				boolean isCredentialsNonExpired = rs.getBoolean("isCredentialsNonExpired");
				String securityQuestion = rs.getString("securityQuestion");
				String securityAnswer = rs.getString("securityAnswer");
				String mfaSecret = rs.getString("mfaSecret");
				String mfaKeyId = rs.getString("mfaKeyId");
				boolean mfaEnabled = rs.getBoolean("mfaEnabled");
				boolean mfaRegistered = rs.getBoolean("mfaRegistered");
				boolean securityQuestionEnabled = rs.getBoolean("securityQuestionEnabled");
				String authority = rs.getString("authority");
				User user = usersByUsername.get(username);
				if(user == null) {
					user = new User(username, password, enabled, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, new ArrayList<>(),
							securityQuestion, securityAnswer, mfaSecret, mfaKeyId, mfaEnabled, mfaRegistered, securityQuestionEnabled);
					usersByUsername.put(username, user);
					usersLijst.add(user);					
				}
				usersLijst.getLast().addAuthority(authority);
			}
			return usersLijst;
		});
	}
}
