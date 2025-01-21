package food_delivery.dto;

import food_delivery.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
	
	private Long id;
	private Integer quantity;
	private Cart cart;
}
