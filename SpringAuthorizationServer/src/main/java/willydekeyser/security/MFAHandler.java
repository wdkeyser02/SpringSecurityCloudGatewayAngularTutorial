package willydekeyser.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class MFAHandler implements AuthenticationSuccessHandler {

	private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final String authority;
	
		
	public MFAHandler(String successUrl, String authority) {
		SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler =
	            new SimpleUrlAuthenticationSuccessHandler(successUrl);
		authenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authority = authority;
	
	}

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		saveAuthentication(request, response, new MFAAuthentication(authentication, authority));
		this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);

	}

	private void saveAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			MFAAuthentication authentication) {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authentication);
		SecurityContextHolder.setContext(securityContext);
		securityContextRepository.saveContext(securityContext, request, response);
	}

}
