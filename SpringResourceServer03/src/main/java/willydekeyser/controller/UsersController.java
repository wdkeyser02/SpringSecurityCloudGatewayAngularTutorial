package willydekeyser.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.entity.Users;
import willydekeyser.service.UsersService;

@RestController
public class UsersController {

	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@GetMapping("/users")
	public List<Users> findAllUsers() {
		return usersService.findAllUsers();
	}
}
