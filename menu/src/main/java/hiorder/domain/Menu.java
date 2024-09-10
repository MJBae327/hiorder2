package hiorder.domain;

import hiorder.MenuApplication;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Menu_table")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer price;

    @ElementCollection
    private List<Long> inventoryId;

    @ElementCollection
    private List<Integer> ingredientUnit;

    @PostPersist
    public void onPostPersist() {
        MenuCreated menuCreated = new MenuCreated(this);
        menuCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        MenuUpdated menuUpdated = new MenuUpdated(this);
        menuUpdated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        MenuDeleted menuDeleted = new MenuDeleted(this);
        menuDeleted.publishAfterCommit();
    }

    public static MenuRepository repository() {
        MenuRepository menuRepository = MenuApplication.applicationContext.getBean(MenuRepository.class);
        return menuRepository;
    }
}
//>>> DDD / Aggregate Root
