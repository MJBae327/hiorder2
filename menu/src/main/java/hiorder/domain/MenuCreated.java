package hiorder.domain;

import hiorder.infra.AbstractEvent;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MenuCreated extends AbstractEvent {

    private Long id;
    private String name;
    private Integer price;
    private List<Long> inventoryId;
    private List<Integer> ingredientUnit;

    public MenuCreated(Menu aggregate) {
        super(aggregate);
    }

    public MenuCreated() {
        super();
    }
}
//>>> DDD / Domain Event
