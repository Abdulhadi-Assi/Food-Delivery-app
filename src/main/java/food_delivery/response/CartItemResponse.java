package food_delivery.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private Long id;
    private Integer quantity;
    private BigDecimal price;
}