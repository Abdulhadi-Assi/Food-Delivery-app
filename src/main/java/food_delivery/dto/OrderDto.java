package food_delivery.dto;

import food_delivery.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;

    private int distinctItemCount;

    private int totalItemQuantity;

    private BigDecimal totalPrice;

    private Restaurant restaurant;

    private Address deliveryAddress;

    private List<OrderItemDTO> items;
}
