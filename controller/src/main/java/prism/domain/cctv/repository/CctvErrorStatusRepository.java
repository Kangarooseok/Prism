package prism.domain.cctv.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CctvErrorStatusRepository {

    private final NamedParameterJdbcTemplate errorJdbc;

    public CctvErrorStatusRepository(@Qualifier("errorJdbc") NamedParameterJdbcTemplate errorJdbc) {
        this.errorJdbc = errorJdbc;
    }

    public static final class Row {
        public final long cctvId;
        public final String status;
        public final Instant occurredAt; // DB의 checked_at 에서 매핑
        public Row(long cctvId, String status, Instant occurredAt) {
            this.cctvId = cctvId; this.status = status; this.occurredAt = occurredAt;
        }
    }

    private static final RowMapper<Row> ROW_MAPPER = (rs, rowNum) -> new Row(
            rs.getLong("cctv_id"),
            rs.getString("status"),
            rs.getTimestamp("checked_at").toInstant()
    );

    /** 특정 CCTV의 "최신" 상태 1건 */
    public Optional<Row> findLatestByCctvId(long cctvId) {
        String sql = """
            SELECT cctv_id, status, checked_at
            FROM fault_events
            WHERE cctv_id = :cctvId
            ORDER BY checked_at DESC
            LIMIT 1
        """;
        List<Row> list = errorJdbc.query(sql, Map.of("cctvId", cctvId), ROW_MAPPER);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}

