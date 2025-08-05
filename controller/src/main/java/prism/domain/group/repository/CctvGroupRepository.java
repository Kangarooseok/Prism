package prism.domain.group.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import prism.domain.group.model.CctvGroup;

// CCTV 그룹 관련 JPA 레포지토리
public interface CctvGroupRepository extends JpaRepository<CctvGroup, Long> {

    // 그룹 이름으로 조회
    Optional<CctvGroup> findByName(String name);

    //
    boolean existsByName(String name);
}
