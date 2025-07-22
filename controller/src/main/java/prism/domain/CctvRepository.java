package prism.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import prism.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "cctvs", path = "cctvs")
public interface CctvRepository
    extends PagingAndSortingRepository<Cctv, Long> {}
