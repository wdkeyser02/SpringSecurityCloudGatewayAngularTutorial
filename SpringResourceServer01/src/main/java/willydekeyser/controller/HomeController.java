package willydekeyser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resourceserver01")
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "Spring Resource Server 01";
	}
}
