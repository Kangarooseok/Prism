package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CctvStatusReadDao {

    private final NamedParameterJdbcTemplate errorJdbc;

    public Map<Long, String> findLatestStatusAsOf(Instant cutoff) {
        String sql = """
            SELECT s.cctv_id, s.status
            FROM fault_events s
            JOIN (
                SELECT cctv_id, MAX(checked_at) AS last_time
                FROM fault_events
                WHERE checked_at <= :cutoff
                GROUP BY cctv_id
            ) t ON t.cctv_id = s.cctv_id AND t.last_time = s.checked_at
        """;

        Map<String, Object> params = Map.of("cutoff", Timestamp.from(cutoff));
        return errorJdbc.query(sql, params, rs -> {
            Map<Long, String> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getLong("cctv_id"), rs.getString("status"));
            }
            return map;
        });
    }
}

