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
	
	public String getGeneratedCode() {
		return generatedCode;
	}
	
	public void saveGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}
}
