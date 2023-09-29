package willydekeyser.entity;

import java.util.Date;
import java.util.Map;

public record Session(
		String id,
		String sesionId,
		Date creationTime,
		Date lastAccessTime,
		Integer maxInactiveInterval,
		Date ExpiryTime,
		String principalName,
		Map<String, String> sessionAtributes
		) {

	public void addAtributes(String attributeName, String attributeBytes) {
		this.sessionAtributes.put(attributeName, attributeBytes);
	}
}
