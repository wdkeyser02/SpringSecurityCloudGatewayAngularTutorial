package willydekeyser.entity;

public record AuthorizationConsent(
		String registeredClientId,
		String principalName,
		String authorities
		) {
}
