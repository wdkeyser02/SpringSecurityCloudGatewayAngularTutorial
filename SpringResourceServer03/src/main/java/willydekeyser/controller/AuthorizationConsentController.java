package willydekeyser.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.entity.AuthorizationConsent;
import willydekeyser.service.AuthorizationConsentService;

@RestController
@RequestMapping("/resourceserver03")
public class AuthorizationConsentController {

	private final AuthorizationConsentService authorizationConsentService;

	public AuthorizationConsentController(AuthorizationConsentService authorizationConsentService) {
		this.authorizationConsentService = authorizationConsentService;
	}
	
	@GetMapping("/authorizationconsent")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public List<AuthorizationConsent> findAllSession() {
		return authorizationConsentService.findAllAuthorizationConsent();
	}
}
