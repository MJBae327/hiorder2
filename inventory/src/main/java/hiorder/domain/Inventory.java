package hiorder.domain;

import hiorder.InventoryApplication;
import hiorder.domain.OutOfStock;
import hiorder.domain.StockCreated;
import hiorder.domain.StockDecreased;
import hiorder.domain.StockDeleted;
import hiorder.domain.StockUpdated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Inventory_table")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer quantity;

    @PostPersist
    public void onPostPersist() {
        StockCreated stockCreated = new StockCreated(this);
        stockCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        StockUpdated stockUpdated = new StockUpdated(this);
        stockUpdated.publishAfterCommit();

        if (this.quantity <= 0) {
            OutOfStock outOfStock = new OutOfStock(this);
            outOfStock.publishAfterCommit();
        }
    }

    @PreRemove
    public void onPreRemove() {
        StockDeleted stockDeleted = new StockDeleted(this);
        stockDeleted.publishAfterCommit();
    }

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = InventoryApplication.applicationContext.getBean(
                InventoryRepository.class
        );
        return inventoryRepository;
    }

    public void decreaseStock(int quantity) {
        this.quantity -= quantity;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
        StockDecreased stockDecreased = new StockDecreased(this);
        stockDecreased.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
