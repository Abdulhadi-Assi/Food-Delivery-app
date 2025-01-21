package food_delivery.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy ="customer")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy ="customer" ,cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<Address> addresses;
    
    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL)
    private List<Order> orders;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

}
