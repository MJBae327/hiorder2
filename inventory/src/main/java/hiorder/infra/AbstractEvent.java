package hiorder.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.client.RestTemplate;

//<<< Clean Arch / Outbound Adaptor
@Data
public class AbstractEvent {

    String eventType;
    Long timestamp;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.gateway}")
    private String gatewayUrl;

    public AbstractEvent(Object aggregate) {
        this();
        BeanUtils.copyProperties(aggregate, this);
    }

    public AbstractEvent() {
        this.setEventType(this.getClass().getSimpleName());
        this.timestamp = System.currentTimeMillis();
    }

    public void publish() {
        String eventEndpoint = gatewayUrl + "/events";
        try {
            String response = restTemplate.postForObject(eventEndpoint, toJson(), String.class);
            System.out.println("Event published successfully: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to publish event: " + e.getMessage());
        }
    }

    public void publishAfterCommit() {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCompletion(int status) {
                        AbstractEvent.this.publish();
                    }
                }
        );
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        return json;
    }
}