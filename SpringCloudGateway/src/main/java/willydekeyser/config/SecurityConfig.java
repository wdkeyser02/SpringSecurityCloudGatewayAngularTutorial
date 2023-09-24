package willydekeyser.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
public class SecurityConfig {

	private final ReactiveClientRegistrationRepository repository;
	
	public SecurityConfig(ReactiveClientRegistrationRepository repository) {
		this.repository = repository;
	}

	@Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http
                .authorizeExchange(exchange -> exchange
                		.pathMatchers("/**").permitAll()
                		.anyExchange().authenticated())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .logout(logout -> logout
                		.logoutUrl("/logout")
                		.logoutSuccessHandler(logoutSuccessHandler(repository)))
                .build();
    }
	
	@Bean
	ServerLogoutSuccessHandler logoutSuccessHandler(ReactiveClientRegistrationRepository repository) {
	       OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(repository);
	       oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logged-out");
	       return oidcLogoutSuccessHandler;
	   }
}
