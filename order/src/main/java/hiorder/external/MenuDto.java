package hiorder.external;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private Long id;
    private String name;
    private Integer price;
    private List<Long> inventoryId;
    private List<Integer> ingredientUnit;
}
