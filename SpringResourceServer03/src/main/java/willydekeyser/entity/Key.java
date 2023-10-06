package willydekeyser.entity;

import java.time.Instant;

public record Key(String id, Instant created, String publicKey, String privateKey) {

}
