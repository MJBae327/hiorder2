package hiorder.domain;

import hiorder.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class OutOfStock extends AbstractEvent {

    private Long id;
}
