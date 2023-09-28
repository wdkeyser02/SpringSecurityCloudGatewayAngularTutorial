package willydekeyser.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
public class SecurityConfig {

	private final ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;
	
	public SecurityConfig(ReactiveClientRegistrationRepository reactiveClientRegistrationRepository) {
		this.reactiveClientRegistrationRepository = reactiveClientRegistrationRepository;
	}

	@Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ServerOAuth2AuthorizationRequestResolver resolver) {
		return http
                .authorizeExchange(exchange -> exchange
                		.pathMatchers("/**").permitAll()
                		.anyExchange().authenticated())
                .oauth2Login(auth -> auth
                		.authorizationRequestResolver(resolver))
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
	
	@Bean
	ServerOAuth2AuthorizationRequestResolver pkceResolver(ReactiveClientRegistrationRepository repo) {
	    var resolver = new DefaultServerOAuth2AuthorizationRequestResolver(repo);
	    resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
	    return resolver;
	}
	
}
