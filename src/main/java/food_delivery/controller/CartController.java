package food_delivery.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import food_delivery.request.AddToCartRequest;
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

	@PostMapping("/{customerId}/add")
	public ResponseEntity<?> addToCart(@Valid @RequestBody AddToCartRequest request, @NotNull @PathVariable Long customerId) {
		cartService.addToCart(customerId, request.getMenuItemId(), request.getQuantity());
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/update-quantity")
	public void updateItemQuantity(Long productId, Long userId) {
		
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
