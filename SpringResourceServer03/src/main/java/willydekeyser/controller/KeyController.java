package willydekeyser.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.entity.Key;
import willydekeyser.service.KeyService;

@RestController
@RequestMapping("/resourceserver03")
public class KeyController {

	private final KeyService keyService;

	public KeyController(KeyService keyService) {
		this.keyService = keyService;
	}
	
	@GetMapping("/key")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public List<Key> findAllSession() {
		return keyService.findAllKey();
	}
}
