package willydekeyser.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
public class SecurityConfig {

	@Bean 
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
			.authorizationEndpoint(authz -> authz
					.consentPage("/oauth2/consent"))
			.oidc(withDefaults());
		http
			.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
			)
			.oauth2ResourceServer((resourceServer) -> resourceServer
				.jwt(withDefaults()));

		return http.build();
	}

	@Bean 
	@Order(2)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/error", "/login").permitAll()
				.anyRequest().authenticated())
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
			);
		return http.build();
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(false)
                .ignoring()
                .requestMatchers("/webjars/**", "/images/**", "/css/**", "/assets/**", "/favicon.ico");
    }
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
//	@Bean
//	OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
//		return context -> {
//			Authentication principal = context.getPrincipal();
//			System.err.println("OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() " + context.getTokenType());
//			if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
//				Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//						.collect(Collectors.toSet());
//				context.getClaims().claim("authorities", authorities);
//			}
//			
//			if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
//				Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//						.collect(Collectors.toSet());
//				context.getClaims().claim("authorities", authorities);
//				context.getClaims().claim("details", "Spring Boot Tutorial");
//			}
//		};
//	}
}
