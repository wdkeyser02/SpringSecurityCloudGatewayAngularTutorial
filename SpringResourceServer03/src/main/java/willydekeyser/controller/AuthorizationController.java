package willydekeyser.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.entity.Authorization;
import willydekeyser.service.AuthorizationService;

@RestController
@RequestMapping("/resourceserver03")
public class AuthorizationController {

	private final AuthorizationService authorizationService;

	public AuthorizationController(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@GetMapping("/authorization")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public List<Authorization> findAllSession() {
		return authorizationService.findAllAuthorization();
	}
}
