package hiorder.domain;

import hiorder.infra.AbstractEvent;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MenuDeleted extends AbstractEvent {

    private Long id;
    private String name;
    private Integer price;
    private List<Long> inventoryId;
    private List<Integer> ingredientUnit;

    public MenuDeleted(Menu aggregate) {
        super(aggregate);
    }

    public MenuDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
