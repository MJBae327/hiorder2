package hiorder.infra;

import hiorder.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    CookRepository cookRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.order}")
    private String orderUrl;

    public void handleOrderCreated(OrderCreated orderCreated) {
        System.out.println("\n\n##### listener CreateCookInfo : " + orderCreated + "\n\n");
        Cook.createCookInfo(orderCreated);
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void pollForOrderCreatedEvents() {
        String orderCreatedEndpoint = orderUrl + "/events/OrderCreated";
        try {
            ResponseEntity<OrderCreated[]> response = restTemplate.getForEntity(orderCreatedEndpoint, OrderCreated[].class);
            OrderCreated[] orderCreatedEvents = response.getBody();
            if (orderCreatedEvents != null) {
                for (OrderCreated event : orderCreatedEvents) {
                    handleOrderCreated(event);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to poll for OrderCreated events: " + e.getMessage());
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
