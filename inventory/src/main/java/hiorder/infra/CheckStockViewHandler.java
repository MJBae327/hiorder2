package hiorder.infra;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckStockViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private CheckStockRepository checkStockRepository;
    //>>> DDD / CQRS
}
