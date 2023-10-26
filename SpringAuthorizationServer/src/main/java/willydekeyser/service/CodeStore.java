package willydekeyser.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
@SessionScope
public class CodeStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnoreProperties
	private String generatedCode;
	
	@JsonIgnoreProperties
	private String base32Secret;
	
	public void saveAll(String generatedCode, String base32Secret) {
		this.generatedCode = generatedCode;
		this.base32Secret = base32Secret;
	}
	
	public String getGeneratedCode() {
		return generatedCode;
	}
	
	public void saveGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}
	
	public String getBase32Secret() {
		return base32Secret;
	}
	
	public void saveBase32Secret(String base32Secret) {
		this.base32Secret = base32Secret;
	}
}
