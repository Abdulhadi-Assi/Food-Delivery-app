package food_delivery.model;

import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import java.io.Serializable;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_details_id")
    private RestaurantDetails restaurantDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy ="restaurant" , fetch = FetchType.LAZY)
    private List<Menu> menus;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false; // Default to false

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point location;
}