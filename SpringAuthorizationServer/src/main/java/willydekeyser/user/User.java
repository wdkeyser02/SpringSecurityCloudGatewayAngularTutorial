package willydekeyser.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public record User(
		String username, 
		String password,
		boolean enabled,
		boolean isAccountNonExpired,
		boolean isAccountNonLocked,
		boolean isCredentialsNonExpired,
		Collection<? extends GrantedAuthority> authorities,
		String securityQuestion,
		String answer,
		String mfaSecret,
		String mfaKeyId,
		boolean mfaEnabled,
		boolean mfaRegistered,
		boolean securityQuestionEnabled
		) implements UserDetails {
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities();
	}
	
	@Override
	public String getPassword() {
		return password();
	}
	
	@Override
	public String getUsername() {
		return username();
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
