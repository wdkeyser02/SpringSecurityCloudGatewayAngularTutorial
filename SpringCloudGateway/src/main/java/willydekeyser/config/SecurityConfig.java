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

	private final ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;
	
	public SecurityConfig(ReactiveClientRegistrationRepository reactiveClientRegistrationRepository) {
		this.reactiveClientRegistrationRepository = reactiveClientRegistrationRepository;
	}

	@Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http
                .authorizeExchange(exchange -> exchange
                		.pathMatchers("/", "/me", "/logout").permitAll()
                		.anyExchange().authenticated())
                .csrf(withDefaults())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .logout(logout -> logout
                		.logoutUrl("/logout")
                		.logoutSuccessHandler(serverLogoutSuccessHandler(reactiveClientRegistrationRepository)))
                .build();
    }
	
	@Bean
	ServerLogoutSuccessHandler serverLogoutSuccessHandler(ReactiveClientRegistrationRepository repository) {
	       OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(repository);
	       oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logged-out");
	       return oidcLogoutSuccessHandler;
	   }
}
