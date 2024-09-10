package hiorder.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.client.RestTemplate;

//<<< Clean Arch / Outbound Adaptor
@Data
public class AbstractEvent {

    String eventType;
    Long timestamp;

    public AbstractEvent(Object aggregate) {
        this();
        BeanUtils.copyProperties(aggregate, this);  // 필요한 필드 복사 로직
    }

    public AbstractEvent() {
        this.setEventType(this.getClass().getSimpleName());
        this.timestamp = System.currentTimeMillis();
    }

    public void publish() {
        // 새 이벤트 발행 로직 (예: HTTP 요청을 통한 발행)
        RestTemplate restTemplate = new RestTemplate();
        String eventEndpoint = "http://example.com/events"; // 이벤트를 보낼 엔드포인트 설정 (적절히 변경 필요)
        try {
            // 이벤트 객체를 JSON으로 변환하여 HTTP 요청으로 전송
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