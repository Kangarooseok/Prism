package prism.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import prism.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "healthCheckLogs",
    path = "healthCheckLogs"
)
public interface HealthCheckLogRepository
    extends PagingAndSortingRepository<HealthCheckLog, LongString> {}
