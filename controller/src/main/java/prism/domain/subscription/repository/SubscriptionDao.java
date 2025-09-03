package prism.domain.subscription.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SubscriptionDao {

    private final NamedParameterJdbcTemplate jdbc;

    public SubscriptionDao(@Qualifier("subscriptionJdbc") NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Long> findManagerIdsByGroup(long groupId) {
        String sql = "SELECT manager_id FROM subscriptions WHERE group_id = :gid";
        return jdbc.query(sql, Map.of("gid", groupId), (rs, i) -> rs.getLong(1));
    }

    public void deleteAllByManager(long managerId) {
        jdbc.update("DELETE FROM subscriptions WHERE manager_id = :mid",
                Map.of("mid", managerId));
    }

    public void deleteOne(long groupId, long managerId) {
        jdbc.update("DELETE FROM subscriptions WHERE group_id = :gid AND manager_id = :mid",
                Map.of("gid", groupId, "mid", managerId));
    }

    public void insertOne(long groupId, long managerId) {
        String sql = "INSERT INTO subscriptions(group_id, manager_id) VALUES (:gid, :mid)";
        jdbc.update(sql, new MapSqlParameterSource()
                .addValue("gid", groupId)
                .addValue("mid", managerId));
    }

    public boolean existsByManager(long managerId) {
        String sql = "SELECT 1 FROM subscriptions WHERE manager_id = :mid LIMIT 1";
        List<Integer> r = jdbc.query(sql, Map.of("mid", managerId), (rs, i) -> 1);
        return !r.isEmpty();
    }

    public int moveAllFromGroupTo(long fromGroupId, long toGroupId) {
        String sql = "UPDATE subscriptions SET group_id = :to WHERE group_id = :from";
        return jdbc.update(sql, Map.of("to", toGroupId, "from", fromGroupId));
    }
}
