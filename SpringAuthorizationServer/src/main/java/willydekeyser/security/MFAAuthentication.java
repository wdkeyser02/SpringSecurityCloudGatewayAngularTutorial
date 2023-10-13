package willydekeyser.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

public class MFAAuthentication extends AnonymousAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private final Authentication primaryAuthentication;

	public MFAAuthentication(Authentication authentication, String authority) {
		super("anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS", authority));
		this.primaryAuthentication = authentication;
	}

	public Authentication getPrimaryAuthentication() {
		return this.primaryAuthentication;
	}
}
