package willydekeyser.config.rotating_keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class InMemoryRsaKeyPairRepository implements RsaKeyPairRepository {

	private final Map<String, RsaKeyPair> idToKeyPair = new HashMap<>();

	@Override
	public List<RsaKeyPair> findKeyPairs() {
		List<RsaKeyPair> result = new ArrayList<>(this.idToKeyPair.values());
		Collections.sort(result, Comparator.comparing(RsaKeyPair::created).reversed());
		return result;
	}

	@Override
	public void delete(String id) {
		this.idToKeyPair.remove(id);
	}

	@Override
	public void save(RsaKeyPair rsaKeyPair) {
		this.idToKeyPair.put(rsaKeyPair.id(), rsaKeyPair);
	}

}
