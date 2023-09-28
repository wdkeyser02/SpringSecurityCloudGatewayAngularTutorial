package willydekeyser.entity;

import java.util.List;

public record Test(String username, String password, Boolean enabled, List<String> authorities) {

	public void addAuthority(String authority) {
		this.authorities.add(authority);
	}	
}
