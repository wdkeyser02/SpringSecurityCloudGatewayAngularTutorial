package willydekeyser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import willydekeyser.user.CustomUserDetailsService;

@Configuration
public class UsersConfig {
	
	@Bean
	CustomUserDetailsService customUserDetailsManager(JdbcTemplate jdbcTemplate) {
		return new CustomUserDetailsService(jdbcTemplate);
	}
	
}
