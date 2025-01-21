package food_delivery.service.impl;


import food_delivery.dto.CartItemDTO;
import food_delivery.dto.CartItemRequestDTO;
import food_delivery.mapper.CartItemMapper;
import food_delivery.model.Cart;
import food_delivery.model.CartItem;
import food_delivery.model.Customer;
import food_delivery.model.MenuItem;
import food_delivery.repository.CartRepository;
import food_delivery.service.CartService;
import food_delivery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final MenuItemServiceImpl menuItemService;

    @Override
    public Cart createCart(Long customerId) {

        Customer customer = customerService.getCustomerById(customerId);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setItems(null);
        cartRepository.save(cart);

        return cart;
    }

    @Override
    public List<CartItemDTO> addItemToCart(CartItemRequestDTO cartItemRequest) {

        Customer customer = customerService.getCustomerById(cartItemRequest.getCustomerId());

        Cart cart = getCustomerCart(cartItemRequest.getCustomerId());

        if(cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cart = saveCart(cart);
        }

        return CartItemMapper.toDtos(cart.getItems());
    }

    @Override
    public List<CartItemDTO> getCart(Long customerId) {
        Cart cart = getCustomerCart(customerId);
        return CartItemMapper.toDtos(cart.getItems());
    }

    @Override
    public Cart getCustomerCart(Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        Cart cart = customer.getCart();

        if(cart == null) cart = createCart(customerId);

        return cart;
    }

    @Override
    public Cart saveCart(Cart newcart) {
        return cartRepository.save(newcart);
    }

    @Override
    public void clearCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void clearCart(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public void addToCart(Long customerId, Long menuItemId, Integer quantity) {

        customerService.getCustomerById(customerId);

        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId,customerId);

        Cart cart = getCustomerCart(customerId);

        cart.addItem(menuItem, quantity);

        cartRepository.save(cart);
    }
    public void addItem(Cart cart,MenuItem menuItem,Integer quantity) {
        var existingItem = cart.getItems().stream()
                .filter(item -> item.getMenuItem().equals(menuItem))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(quantity);
        } else {
            cart.getItems().add(new CartItem(null , cart,menuItem,quantity,menuItem.getPrice()));
        }
    }
}
