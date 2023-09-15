package willydekeyser.controller;

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
			return Mono.just(
					new UserDto(
							principal.getIdToken().getSubject(),
							principal.getIdToken().getClaim("sub"),
							""));
		}
		return Mono.just(UserDto.ANONYMOUS);
	}
	
	static record UserDto(String username, String details, String roles) {
		static final UserDto ANONYMOUS = new UserDto("", "", "");
	}
}
