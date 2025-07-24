package prism.domain.cctv.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prism.domain.cctv.model.Cctv;

@Repository
public interface CctvRepository extends CrudRepository<Cctv, Long> {
}
