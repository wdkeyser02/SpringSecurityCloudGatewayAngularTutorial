package willydekeyser.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import willydekeyser.entity.Session;

@Service
public class SessionService {

private final JdbcTemplate jdbcTemplate;
	
	public SessionService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Session> findAllSession() {
		String sql = "SELECT PRIMARY_ID, SESSION_ID, CREATION_TIME, LAST_ACCESS_TIME, MAX_INACTIVE_INTERVAL, "
				+ "EXPIRY_TIME, PRINCIPAL_NAME, SESSION_PRIMARY_ID, ATTRIBUTE_NAME, ATTRIBUTE_BYTES "
				+ "FROM SPRING_SESSION, SPRING_SESSION_ATTRIBUTES "
				+ "WHERE SPRING_SESSION.PRIMARY_ID = SPRING_SESSION_ATTRIBUTES.SESSION_PRIMARY_ID;";
		List<Session> sessionsLijst = new ArrayList<>();
		return jdbcTemplate.query(sql, rs -> {
			Map<String, Session> sessionsByPrimaryId = new HashMap<>();
			while (rs.next()) {
				String primaryId = rs.getString("PRIMARY_ID");
				String sessionId = rs.getString("SESSION_ID");
				Date creationTime = new Date(rs.getLong("CREATION_TIME"));
				Date lastAccessTime = new Date(rs.getLong("LAST_ACCESS_TIME"));
				Integer maxInactiveInterval = rs.getInt("MAX_INACTIVE_INTERVAL");
				Date expiryTime = new Date(rs.getLong("EXPIRY_TIME"));
				String principalName = rs.getString("PRINCIPAL_NAME");
				String attributeName = rs.getString("ATTRIBUTE_NAME");
				String attributeBytes = rs.getString("ATTRIBUTE_BYTES");
				Session session = sessionsByPrimaryId.get(primaryId);
				if(session == null) {
					session = new Session(primaryId, sessionId, creationTime, lastAccessTime, maxInactiveInterval, expiryTime, principalName, new HashMap<>());
					sessionsByPrimaryId.put(primaryId, session);
					sessionsLijst.add(session);					
				}
				sessionsLijst.getLast().addAtributes(attributeName, attributeBytes);
			}
			return sessionsLijst;
		});
	}
}