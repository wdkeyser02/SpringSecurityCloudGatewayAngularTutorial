package willydekeyser;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordEncoder {
	
	@Test
	void test() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(14);
		String password = "secret";
		System.out.println(passwordEncoder.encode(password));
	}

}
