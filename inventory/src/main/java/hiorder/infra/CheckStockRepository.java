package hiorder.infra;

import hiorder.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "checkStocks",
    path = "checkStocks"
)
public interface CheckStockRepository
    extends PagingAndSortingRepository<CheckStock, Long> {}
