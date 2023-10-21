package willydekeyser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.BytesEncryptor;

import willydekeyser.user.CustomUserDetailsService;

@Configuration
public class UsersConfig {
	
	@Bean
	CustomUserDetailsService customUserDetailsManager(JdbcTemplate jdbcTemplate, BytesEncryptor bytesEncryptor) {
		return new CustomUserDetailsService(jdbcTemplate, bytesEncryptor);
	}
	
}
