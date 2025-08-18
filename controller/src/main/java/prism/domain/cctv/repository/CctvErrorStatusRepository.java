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

    // errorJdbc 빈을 명시적으로 주입 (ReadDbConfig에서 만든 이름과 동일)
    public CctvErrorStatusRepository(@Qualifier("errorJdbc") NamedParameterJdbcTemplate errorJdbc) {
        this.errorJdbc = errorJdbc;
    }

    // 반환용 간단 DTO (필요시 나중에 별도 파일로 분리)
    public static final class Row {
        public final long cctvId;
        public final String status;
        public final Instant occurredAt;
        public Row(long cctvId, String status, Instant occurredAt) {
            this.cctvId = cctvId; this.status = status; this.occurredAt = occurredAt;
        }
    }

    private static final RowMapper<Row> ROW_MAPPER = new RowMapper<>() {
        @Override public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(
                    rs.getLong("cctv_id"),
                    rs.getString("status"),
                    rs.getTimestamp("occurred_at").toInstant()
            );
        }
    };

    /** 특정 CCTV의 "최신" 오류 상태 1건 */
    public Optional<Row> findLatestByCctvId(long cctvId) {
        String sql = """
            SELECT cctv_id, status, occurred_at
            FROM cctv_error_status
            WHERE cctv_id = :cctvId
            ORDER BY occurred_at DESC
            LIMIT 1
        """;
        List<Row> list = errorJdbc.query(sql, Map.of("cctvId", cctvId), ROW_MAPPER);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
