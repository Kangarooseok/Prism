// prism/infra/service/CctvLiveStatusService.java
package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
@RequiredArgsConstructor
public class CctvLiveStatusService {

    @Qualifier("errorJdbc")
    private final NamedParameterJdbcTemplate errorJdbc;

    public String resolveStatusOrActive(Long cctvId) {
        final String sql = """
            SELECT status
            FROM fault_events
            WHERE cctv_id = :id
            ORDER BY checked_at DESC
            LIMIT 1
        """;
        try {
            return errorJdbc.query(sql, new MapSqlParameterSource("id", cctvId), rs -> {
                if (rs.next()) return normalize(rs.getString("status"));
                return "ACTIVE"; // 레코드 없으면 ACTIVE
            });
        } catch (DataAccessException ex) {
            return "ACTIVE";
        }
    }

    private String normalize(String raw) {
        if (raw == null) return "ACTIVE";
        switch (raw.trim().toUpperCase()) {
            case "ONLINE", "OK", "ACTIVE":   return "ACTIVE";
            case "WARNING", "WARN", "ALERT": return "WARNING";
            case "OFFLINE", "ERROR", "DOWN", "FAIL": return "OFFLINE";
            default: return "ACTIVE";
        }
    }
}

