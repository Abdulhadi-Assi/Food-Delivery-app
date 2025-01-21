package food_delivery.service;

import java.util.List;
import org.springframework.stereotype.Service;
import food_delivery.dto.CartItemDTO;
import food_delivery.dto.CartItemRequestDTO;
import food_delivery.model.Cart;
import javax.validation.constraints.NotNull;


@Service
public interface CartService {

	public Cart createCart(Long customerId);
	
	public List<CartItemDTO> addItemToCart(CartItemRequestDTO cartItemRequest);
	
	public List<CartItemDTO> getCart(Long customerId);

	public Cart getCustomerCart(Long customerId);
	
	public Cart saveCart(Cart newcart);

	public void clearCart(Long cartId);

	public void clearCart(Cart cart);

    void addToCart(@NotNull Long customerId, @NotNull Long menuItemId, @NotNull Integer quantity);
}
