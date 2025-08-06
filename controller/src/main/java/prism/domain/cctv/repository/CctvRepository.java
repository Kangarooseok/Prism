package prism.domain.cctv.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prism.domain.cctv.model.Cctv;

import java.util.List;
import java.util.Date;

@Repository
public interface CctvRepository extends CrudRepository<Cctv, Long> {

    // 특정 그룹에 속한 CCTV 목록 조회
    List<Cctv> findByGroupId(Long groupId);

    // 날짜 기준 등록 수 카운트
    @Query("SELECT COUNT(c) FROM Cctv c WHERE c.createdAt BETWEEN :start AND :end")
    Long countByCreatedAtBetween(@Param("start") Date start, @Param("end") Date end);
}