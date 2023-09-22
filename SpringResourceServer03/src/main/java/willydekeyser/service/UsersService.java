package willydekeyser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import willydekeyser.entity.Users;

@Service
public class UsersService {
	
	private final JdbcTemplate jdbcTemplate;
	
	public UsersService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Users> findAllUsers() {
		var sql = "SELECT user.username, user.password, user.enabled, authorities.authority FROM users user LEFT JOIN authorities on user.username = authorities.username";
		List<Users> usersLijst = new ArrayList<>();
		return jdbcTemplate.query(sql, rs -> {
			Map<String, Users> usersByUsername = new HashMap<>();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				Boolean enabled = rs.getBoolean("enabled");
				String authority = rs.getString("authority");
				Users user = usersByUsername.get(username);
				if(user == null) {
					user = new Users(username, password, enabled, new ArrayList<>());
					usersByUsername.put(username, user);
					usersLijst.add(user);					
				}
				usersLijst.getLast().addAuthority(authority);
			}
			return usersLijst;
		});
	}
}
