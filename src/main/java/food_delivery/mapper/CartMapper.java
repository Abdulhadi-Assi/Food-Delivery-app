package food_delivery.mapper;

import food_delivery.dto.CartDTO;
import food_delivery.model.Cart;

public class CartMapper {
	
	
	public  static CartDTO toDTO (Cart cart) {
		if(cart== null)return null;
		
		return CartDTO.builder().id(cart.getId()).build() ;
	}

}
