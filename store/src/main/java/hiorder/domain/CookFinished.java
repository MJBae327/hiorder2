package hiorder.domain;

import hiorder.infra.AbstractEvent;

import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CookFinished extends AbstractEvent {

    private Long id;
    private Long tableId;
    private Long menuId;
    private Integer quantity;
    private String status;

    public CookFinished(Cook aggregate) {
        super(aggregate);
    }

    public CookFinished() {
        super();
    }
}
//>>> DDD / Domain Event
