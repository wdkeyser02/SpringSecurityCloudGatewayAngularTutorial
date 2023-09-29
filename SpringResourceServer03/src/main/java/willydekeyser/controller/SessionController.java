package willydekeyser.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.entity.Session;
import willydekeyser.service.SessionService;

@RestController
@RequestMapping("/resourceserver03")
public class SessionController {

	private final SessionService sessionService;

	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	@GetMapping("/sessions")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public List<Session> findAllSession() {
		return sessionService.findAllSession();
	}
}