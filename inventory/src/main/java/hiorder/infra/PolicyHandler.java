package hiorder.infra;

import hiorder.domain.*;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.order}")
    private String orderUrl;

    public void handleOrderCreated(OrderCreated orderCreated) {
        System.out.println("Handling OrderCreated event: " + orderCreated.toString());
        inventoryRepository.findById(Long.valueOf(orderCreated.getMenuId()))
                .ifPresent(inventory -> {
                    inventory.decreaseStock(orderCreated.getQuantity());
                    inventoryRepository.save(inventory);
                });
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
