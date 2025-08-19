package prism.domain.cctv.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prism.domain.cctv.model.Cctv;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;

@Repository
public interface CctvRepository extends CrudRepository<Cctv, Long> {

    // 특정 그룹에 속한 CCTV 목록 조회
    List<Cctv> findByGroupId(Long groupId);

    // 날짜 기준 등록 수 카운트
    @Query("SELECT COUNT(c) FROM Cctv c WHERE c.createdAt BETWEEN :start AND :end")
    Long countByCreatedAtBetween(@Param("start") Date start, @Param("end") Date end);

    // 현재 총보유(삭제되지 않은 레코드) — @Where에 의존하지 않도록 네이티브로 수행
    @Query(value = "SELECT COUNT(*) FROM cctv WHERE deleted_at IS NULL", nativeQuery = true)
    long countActiveNative();

    //  특정 시점(cutoff) 기준 총보유:
    //    created_at <= cutoff AND (deleted_at IS NULL OR deleted_at > cutoff)
    //    => '그 시점에 존재하던' 수량
    @Query(
            value = "SELECT COUNT(*) FROM cctv " +
                    "WHERE created_at <= :cutoff " +
                    "AND (deleted_at IS NULL OR deleted_at > :cutoff)",
            nativeQuery = true
    )
    long countAsOf(@Param("cutoff") Timestamp cutoff);

    @Query(value = "SELECT COUNT(*) FROM cctv WHERE status = :status AND deleted_at IS NULL", nativeQuery = true)
    long countByStatusNow(@Param("status") String status);

    // --- 추가: 상태별 '특정일 종료 시점' 집계 ---
    @Query(value = """
            SELECT COUNT(*) 
            FROM cctv 
            WHERE status = :status
              AND created_at <= :cutoff 
              AND (deleted_at IS NULL OR deleted_at > :cutoff)
            """, nativeQuery = true)
    long countByStatusAsOf(@Param("status") String status, @Param("cutoff") Timestamp cutoff);

    @Query(value = """
    SELECT id FROM cctv
    WHERE created_at <= :cutoff
      AND (deleted_at IS NULL OR deleted_at > :cutoff)
""", nativeQuery = true)
    List<Long> findIdsAsOf(@Param("cutoff") Timestamp cutoff);
}
