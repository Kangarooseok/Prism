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
            FROM cctv_error_status
            WHERE cctv_id = :id
            ORDER BY occurred_at DESC
            LIMIT 1
        """;
        try {
            return errorJdbc.query(sql, new MapSqlParameterSource("id", cctvId), rs -> {
                if (rs.next()) return rs.getString("status");
                return "ACTIVE"; // 레코드가 없으면 ACTIVE
            });
        } catch (DataAccessException ex) {
            // 장애판별 DB가 잠깐 죽어도 UI가 망가지지 않게 ACTIVE로 폴백
            return "ACTIVE";
        }
    }
}
