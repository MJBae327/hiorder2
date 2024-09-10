package hiorder.domain;

import hiorder.infra.AbstractEvent;

import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Rejected extends AbstractEvent {

    private Long id;
    private Long tableId;
    private Long menuId;
    private Integer quantity;
    private String status;

    public Rejected(Cook aggregate) {
        super(aggregate);
    }

    public Rejected() {
        super();
    }
}
//>>> DDD / Domain Event
