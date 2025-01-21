package food_delivery.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDTO {
	
	@NotNull
	private Long customerId;
	@NotNull
	private Long menuItemId;
	@Min(1)
	private Integer quantity;
	
	private String itemDescription;
}
