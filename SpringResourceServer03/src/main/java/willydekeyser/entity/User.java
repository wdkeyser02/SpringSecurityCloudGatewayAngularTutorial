package willydekeyser.entity;

import java.util.List;

public record User(
		String username, 
		String password, 
		boolean enabled,
		boolean isAccountNonExpired,
		boolean isAccountNonLocked,
		boolean isCredentialsNonExpired,
		List<String> authorities,
		String securityQuestion,
		String answer,
		String mfaSecret,
		String mfaKeyId,
		boolean mfaEnabled,
		boolean mfaRegistered,
		boolean securityQuestionEnabled
		) {
	
	public void addAuthority(String authority) {
		this.authorities.add(authority);
	}	
}