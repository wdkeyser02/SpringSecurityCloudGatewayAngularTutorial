package willydekeyser.entity;

import java.util.List;

public class Users {

	private String username;
	private String password;
	private Boolean enabled;
	private List<String> authorities;
	
	public Users() {
	}
	
	public Users(String username, String password, Boolean enabled, List<String> authorities) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.authorities = authorities;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<String> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	
	public void addAuthority(String authority) {
		this.authorities.add(authority);
	}

}
