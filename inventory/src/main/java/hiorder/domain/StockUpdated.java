package hiorder.domain;

import hiorder.infra.AbstractEvent;

import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class StockUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private Integer quantity;

    public StockUpdated(Inventory aggregate) {
        super(aggregate);
    }

    public StockUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
