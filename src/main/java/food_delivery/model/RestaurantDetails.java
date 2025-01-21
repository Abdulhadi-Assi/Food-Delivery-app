package food_delivery.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="restaurant_details")
public class RestaurantDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "restaurantDetails")
    private Restaurant restaurant;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "capacity")
    private Integer capacity;

}