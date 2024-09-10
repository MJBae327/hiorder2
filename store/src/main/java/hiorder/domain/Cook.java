package hiorder.domain;

import hiorder.StoreApplication;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cook_table")
@Data
public class Cook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tableId;
    private Long menuId;
    private Integer quantity;
    private String status;

    @PostPersist
    public void onPostPersist() {
        Rejected rejected = new Rejected(this);
        rejected.publishAfterCommit();
    }

    public static CookRepository repository() {
        CookRepository cookRepository = StoreApplication.applicationContext.getBean(CookRepository.class);
        return cookRepository;
    }

    public void isAccept(IsAcceptCommand isAcceptCommand) {
        if (isAcceptCommand.getIsaccept()) {
            this.setStatus("ACCEPTED");
            Accepted accepted = new Accepted(this);
            accepted.publishAfterCommit();
        } else {
            this.setStatus("REJECTED");
            Rejected rejected = new Rejected(this);
            rejected.publishAfterCommit();
        }
    }

    public void start() {
        this.setStatus("COOKING");
        CookStarted cookStarted = new CookStarted(this);
        cookStarted.publishAfterCommit();
    }

    public void finish() {
        this.setStatus("FINISHED");
        CookFinished cookFinished = new CookFinished(this);
        cookFinished.publishAfterCommit();
    }

    public static void createCookInfo(OrderCreated orderCreated) {
        Cook cook = new Cook();
        cook.setTableId(orderCreated.getTableId());
        cook.setMenuId(orderCreated.getMenuId().longValue());
        cook.setQuantity(orderCreated.getQuantity());
        cook.setStatus("CREATED");
        repository().save(cook);
    }
}
//>>> DDD / Aggregate Root
