package prism.domain.cctv.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prism.domain.cctv.model.Cctv;

import java.util.List;

@Repository
public interface CctvRepository extends CrudRepository<Cctv, Long> {

    // 특정 그룹에 속한 CCTV 목록 조회
    List<Cctv> findByGroupId(Long groupId);
}
