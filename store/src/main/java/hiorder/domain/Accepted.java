package hiorder.domain;

import hiorder.infra.AbstractEvent;

import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Accepted extends AbstractEvent {

    private Long id;
    private Long tableId;
    private Long menuId;
    private Integer quantity;
    private String status;

    public Accepted(Cook aggregate) {
        super(aggregate);
    }

    public Accepted() {
        super();
    }
}
//>>> DDD / Domain Event
