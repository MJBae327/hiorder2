package hiorder.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hiorder.domain.*;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    OrderRepository orderRepository;

    public void wheneverCookStarted_UpdateStatus(
        @Payload CookStarted cookStarted
    ) {
        CookStarted event = cookStarted;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + cookStarted + "\n\n"
        );

        // Sample Logic //
        Order.updateStatus(event);
    }


    public void wheneverCookFinished_UpdateStatus(
        @Payload CookFinished cookFinished
    ) {
        CookFinished event = cookFinished;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + cookFinished + "\n\n"
        );

        // Sample Logic //
        Order.updateStatus(event);
    }

    public void wheneverRejected_UpdateStatus(@Payload Rejected rejected) {
        Rejected event = rejected;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + rejected + "\n\n"
        );

        // Sample Logic //
        Order.updateStatus(event);
    }

    public void wheneverAccepted_UpdateStatus(@Payload Accepted accepted) {
        Accepted event = accepted;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + accepted + "\n\n"
        );

        // Sample Logic //
        Order.updateStatus(event);
    }

    public void wheneverOutOfStock_UpdateIsOrderable(
        @Payload OutOfStock outOfStock
    ) {
        OutOfStock event = outOfStock;
        System.out.println(
            "\n\n##### listener UpdateIsOrderable : " + outOfStock + "\n\n"
        );

        // Sample Logic //
        Order.updateIsOrderable(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
