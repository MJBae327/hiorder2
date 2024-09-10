package hiorder.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hiorder.domain.*;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
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
    OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.store}")
    private String storeUrl;

    public void handleCookStarted(CookStarted cookStarted) {
        System.out.println("\n\n##### listener UpdateStatus : " + cookStarted + "\n\n");
        Order.updateStatus(cookStarted);
    }

    public void handleCookFinished(CookFinished cookFinished) {
        System.out.println("\n\n##### listener UpdateStatus : " + cookFinished + "\n\n");
        Order.updateStatus(cookFinished);
    }

    public void handleRejected(Rejected rejected) {
        System.out.println("\n\n##### listener UpdateStatus : " + rejected + "\n\n");
        Order.updateStatus(rejected);
    }

    public void handleAccepted(Accepted accepted) {
        System.out.println("\n\n##### listener UpdateStatus : " + accepted + "\n\n");
        Order.updateStatus(accepted);
    }

    public void handleOutOfStock(OutOfStock outOfStock) {
        System.out.println("\n\n##### listener UpdateIsOrderable : " + outOfStock + "\n\n");
        Order.updateIsOrderable(outOfStock);
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void pollForStoreEvents() {
        String storeEventsEndpoint = storeUrl + "/events";
        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity(storeEventsEndpoint, Object[].class);
            Object[] events = response.getBody();
            if (events != null) {
                for (Object event : events) {
                    handleStoreEvent(event);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to poll for store events: " + e.getMessage());
        }
    }

    private void handleStoreEvent(Object event) {
        if (event instanceof CookStarted) {
            handleCookStarted((CookStarted) event);
        } else if (event instanceof CookFinished) {
            handleCookFinished((CookFinished) event);
        } else if (event instanceof Rejected) {
            handleRejected((Rejected) event);
        } else if (event instanceof Accepted) {
            handleAccepted((Accepted) event);
        } else if (event instanceof OutOfStock) {
            handleOutOfStock((OutOfStock) event);
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
