package willydekeyser.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class UserInfo {

	@GetMapping("/me")
	public Mono<UserDto> userInfo(@AuthenticationPrincipal OidcUser principal) {
		if (principal instanceof OidcUser) {
			List<String> list = principal.getIdToken().getClaimAsStringList("authorities");
			String authorities = ""; 
			if (list != null) {
				authorities = list.stream()
						.collect(Collectors.joining(","));
			}
			return Mono.just(
					new UserDto(
							principal.getIdToken().getSubject(),
							principal.getIdToken().getClaim("details"),
							authorities));
		}
		return Mono.just(UserDto.ANONYMOUS);
	}
	
	static record UserDto(String username, String details, String roles) {
		static final UserDto ANONYMOUS = new UserDto("ANONYMOUS", "Spring Boot Tutorial", "NONE");
	}
}
