package food_delivery.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "distinct_item_count")
    private Integer distinctItemCount;

    @Column(name = "total_item_quantity")
    private Integer totalItemQuantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    
    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @OneToMany(mappedBy="order" , cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "notes")
    private String notes;

    public void addItems(OrderItem orderItem)
    {
        items.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeItem(OrderItem orderItem) {
        items.remove(orderItem);
        orderItem.setOrder(null);
    }
}
