package willydekeyser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class RegisteredClientRepositoryConfig {

	@Bean
	RegisteredClientRepository jdbcRegisteredClientRepository(JdbcTemplate template) {
		return new JdbcRegisteredClientRepository(template);
	}
}
