package willydekeyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.RedirectView;

@Controller
public class LoginController {

	@GetMapping("logged-out")
	public RedirectView logout() {
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("/");
	    return redirectView;
	}
}
