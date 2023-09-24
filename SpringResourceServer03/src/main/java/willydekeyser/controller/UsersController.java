package willydekeyser.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.entity.Users;
import willydekeyser.service.UsersService;

@RestController
@RequestMapping("/resourceserver03")
public class UsersController {

	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@GetMapping("/users")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public List<Users> findAllUsers() {
		return usersService.findAllUsers();
	}
	
}
