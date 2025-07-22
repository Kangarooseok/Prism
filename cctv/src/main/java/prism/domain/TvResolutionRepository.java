package prism.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import prism.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "tvResolutions",
    path = "tvResolutions"
)
public interface TvResolutionRepository
    extends PagingAndSortingRepository<TvResolution, Long> {}
