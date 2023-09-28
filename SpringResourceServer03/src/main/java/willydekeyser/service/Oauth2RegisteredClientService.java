package willydekeyser.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import willydekeyser.entity.Oauth2RegisteredClient;

@Service
public class Oauth2RegisteredClientService {

	private final JdbcTemplate jdbcTemplate;
	private ObjectMapper objectMapper = new ObjectMapper();

	public Oauth2RegisteredClientService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Oauth2RegisteredClient> findAllClients() {
		var sql = "SELECT * FROM oauth2_registered_client";
		return jdbcTemplate.query(sql, new RowMapper<Oauth2RegisteredClient>() {

			@Override
			public Oauth2RegisteredClient mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oauth2RegisteredClient client = new Oauth2RegisteredClient(
						rs.getString("id"), 
						rs.getString("client_id"), 
						rs.getString("client_secret"), 
						new Date(rs.getTimestamp("client_id_issued_at").getTime()), 
						new Date(rs.getTimestamp("client_secret_expires_at").getTime()), 
						rs.getString("client_name"),
						StringUtils.commaDelimitedListToSet(rs.getString("client_authentication_methods")),
						StringUtils.commaDelimitedListToSet(rs.getString("authorization_grant_types")),
						StringUtils.commaDelimitedListToSet(rs.getString("redirect_uris")),
						StringUtils.commaDelimitedListToSet(rs.getString("post_logout_redirect_uris")),
						StringUtils.commaDelimitedListToSet(rs.getString("scopes")),
						parseMap(rs.getString("client_settings")),
						parseMap(rs.getString("token_settings"))
						);
				return client;
			}	
		});			
	}
	
	private Map<String, Object> parseMap(String data) {
		try {
			return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}
}
