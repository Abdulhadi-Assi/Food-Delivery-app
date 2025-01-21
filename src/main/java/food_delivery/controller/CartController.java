package food_delivery.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import food_delivery.request.CartItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import food_delivery.dto.CartItemDTO;
import food_delivery.service.CartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
@RestController
public class CartController {
	
	private final CartService cartService;

	@PostMapping({"/{customerId}/add","/{customerId}/update-quantity"})
	public ResponseEntity<?> addToCart(@Valid @RequestBody CartItemRequest request, @NotNull @PathVariable Long customerId) {
		cartService.addToCart(customerId, request.getMenuItemId(), request.getQuantity());
		return ResponseEntity.ok().build();
	}

	
	@GetMapping("/{customerId}")
	public ResponseEntity<List<CartItemDTO>> getCart(@NotNull @PathVariable Long customerId) {
		return ResponseEntity.ok(cartService.getCart(customerId)) ;
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<?> clearCart(@NotNull @PathVariable Long customerId)
	{
		cartService.createCart(customerId);
		return ResponseEntity.ok().build();
	}

}
