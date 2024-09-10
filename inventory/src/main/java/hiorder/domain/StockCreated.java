package hiorder.domain;

import hiorder.infra.AbstractEvent;

import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class StockCreated extends AbstractEvent {

    private Long id;
    private String name;
    private Integer quantity;

    public StockCreated(Inventory aggregate) {
        super(aggregate);
    }

    public StockCreated() {
        super();
    }
}
//>>> DDD / Domain Event
