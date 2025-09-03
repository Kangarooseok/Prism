package prism.infra.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UnassignedBackfillRunner implements CommandLineRunner {

    private static final long UNASSIGNED_GROUP_ID = 2L;

    @Qualifier("userJdbc")
    private final org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate userJdbc;

    @Qualifier("subscriptionJdbc")
    private final org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate subscriptionJdbc;

    @Override
    @Transactional(transactionManager = "subscriptionTxManager") // 구독 DB 트랜잭션
    public void run(String... args) {
        // 1) 활성 사용자 id 전부 (※ 실제 테이블명이 users가 맞는지 확인)
        List<Long> activeUserIds = userJdbc.getJdbcTemplate().query(
                "SELECT id FROM users WHERE status = 'active'",
                (rs, i) -> rs.getLong(1)
        );
        if (activeUserIds.isEmpty()) return;

        // 2) 이미 어떤 그룹이든 배정된 manager_id (중복 배정 방지)
        Set<Long> assigned = new HashSet<>(subscriptionJdbc.getJdbcTemplate().query(
                "SELECT DISTINCT manager_id FROM subscriptions",
                (rs, i) -> rs.getLong(1)
        ));

        // 3) 아직 한 줄도 없는 사용자만 미정(2)으로 배정
        List<Long> toInsert = activeUserIds.stream()
                .filter(id -> !assigned.contains(id))
                .toList();
        if (toInsert.isEmpty()) return;

        String sql = "INSERT INTO subscriptions (group_id, manager_id) VALUES (?, ?)";

        JdbcTemplate jt = subscriptionJdbc.getJdbcTemplate();
        jt.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override public int getBatchSize() { return toInsert.size(); }
            @Override public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, UNASSIGNED_GROUP_ID);
                ps.setLong(2, toInsert.get(i));
            }
        });
    }
}
