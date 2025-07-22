package prism.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import prism.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "networkActions",
    path = "networkActions"
)
public interface NetworkActionRepository
    extends PagingAndSortingRepository<NetworkAction, Long> {}
