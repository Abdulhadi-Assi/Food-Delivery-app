package food_delivery.mapper;

import java.util.List;
import java.util.stream.Collectors;
import food_delivery.dto.CartItemDTO;
import food_delivery.model.CartItem;

public class CartItemMapper {
	
	public static CartItemDTO toDto(CartItem cartItem) {
		
		return CartItemDTO.builder()
				.id(cartItem.getId())
				.quantity(cartItem.getQuantity())
				.cart(cartItem.getCart())
				.build();
	}
	
	
	public static List<CartItemDTO> toDtos(List<CartItem> cartItems) {
		
		return cartItems.stream().map(CartItemMapper::toDto).collect(Collectors.toList());
	}

}
