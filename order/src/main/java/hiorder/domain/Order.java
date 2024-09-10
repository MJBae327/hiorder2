package hiorder.domain;

import hiorder.OrderApplication;
import hiorder.domain.OrderCreated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numberOfPeople;
    private Long tableId;
    private Date orderTime;
    private Integer menuId;
    private Integer quantity;
    private Integer price;
    private String status;
    private String isOrderable;

    @PostPersist
    public void onPostPersist() {
        OrderCreated orderCreated = new OrderCreated(this);
        orderCreated.publishAfterCommit();
    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }

    public static void updateStatus(CookStarted cookStarted) {
        repository().findById(cookStarted.getId()).ifPresent(order -> {
            order.setStatus("COOKING");
            repository().save(order);
        });
    }

    public static void updateStatus(CookFinished cookFinished) {
        repository().findById(cookFinished.getId()).ifPresent(order -> {
            order.setStatus("COOKED");
            repository().save(order);
        });
    }

    public static void updateStatus(Rejected rejected) {
        repository().findById(rejected.getId()).ifPresent(order -> {
            order.setStatus("REJECTED");
            repository().save(order);
        });
    }

    public static void updateStatus(Accepted accepted) {
        repository().findById(accepted.getId()).ifPresent(order -> {
            order.setStatus("ACCEPTED");
            repository().save(order);
        });
    }

    public static void updateIsOrderable(OutOfStock outOfStock) {
        repository().findAll().forEach(order -> {
            if (order.getMenuId().equals(outOfStock.getId())) {
                order.setIsOrderable("NO");
                repository().save(order);
            }
        });
    }
}
//>>> DDD / Aggregate Root
