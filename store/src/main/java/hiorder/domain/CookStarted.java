package hiorder.domain;

import hiorder.infra.AbstractEvent;

import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CookStarted extends AbstractEvent {

    private Long id;
    private Long tableId;
    private Long menuId;
    private Integer quantity;
    private String status;

    public CookStarted(Cook aggregate) {
        super(aggregate);
    }

    public CookStarted() {
        super();
    }
}
//>>> DDD / Domain Event
