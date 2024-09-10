package hiorder.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "cooks", path = "cooks")
public interface CookRepository
    extends PagingAndSortingRepository<Cook, Long> {}
