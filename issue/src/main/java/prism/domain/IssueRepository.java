package prism.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import prism.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "issues", path = "issues")
public interface IssueRepository
    extends PagingAndSortingRepository<Issue, Long> {}
