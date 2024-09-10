package hiorder.domain;

import hiorder.infra.AbstractEvent;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MenuUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private Integer price;
    private List<Long> inventoryId;
    private List<Integer> ingredientUnit;

    public MenuUpdated(Menu aggregate) {
        super(aggregate);
    }

    public MenuUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
