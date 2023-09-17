package willydekeyser.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http
                .authorizeExchange(exchange -> exchange
                		.pathMatchers("/**").permitAll()
                		.anyExchange().authenticated())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .build();
    }
}
