package hiorder.domain;


import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "CheckStock_table")
@Data
public class CheckStock {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
}
