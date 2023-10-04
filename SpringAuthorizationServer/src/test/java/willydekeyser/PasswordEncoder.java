package willydekeyser;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordEncoder {
	
	@Test
	void createPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(14);
		String password = "secret";
		System.out.println(passwordEncoder.encode(password));
	}
	
	@Test
	void create() {
		String key = UUID.randomUUID().toString();
		System.out.println(key);
	}

}
