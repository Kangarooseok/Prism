package prism.domain.user.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import prism.domain.user.dto.UserDto;

import java.util.List;
import java.util.Map;

@Repository
public class UserReadDao {

    private final NamedParameterJdbcTemplate userJdbc;

    public UserReadDao(@Qualifier("userJdbc") NamedParameterJdbcTemplate userJdbc) {
        this.userJdbc = userJdbc;
    }

    public List<UserDto> findAllActive() {
        // 테이블명이 다르면 users -> 실제 테이블명으로 변경
        String sql = """
            SELECT id, username, email, role, status
            FROM users
            WHERE status = 'active'
        """;
        return userJdbc.query(sql, Map.of(), (rs, i) ->
                new UserDto(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("status")
                )
        );
    }
}
