package hiorder.domain;

import hiorder.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class Rejected extends AbstractEvent {

    private Long id;
    private Long tableId;
    private Long menuId;
    private Integer quantity;
    private String status;
}
