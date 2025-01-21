package food_delivery.service;


import food_delivery.exception.ApplicationErrorEnum;
import food_delivery.exception.BusinessException;
import food_delivery.model.*;
import food_delivery.repository.OrderRepository;
import food_delivery.repository.OrderStatusRepository;
import food_delivery.repository.OrderTrackingRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Data
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final CartService cartService;
    private final MenuItemService menuItemService;

    private final OrderTrackingRepository orderTrackingRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Transactional
    public Order createOrder(Long customerId , Long addressId) {

        Customer customer = customerService.getCustomerById(customerId).orElseThrow(
                ()-> new RuntimeException("customer does not exist")
        );
        Cart cart = cartService.getCustomerCart(customerId);

        //create new order
        Order order = new Order();
        order.setCustomer(customer);

        //set order address
        order.setDeliveryAddress(
                customer.getAddresses().stream()
                .filter(address -> address.getAddressId().equals(addressId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("address not found"))
        );

        // set restaurant as provider of first menu item ordered
        // cart should have only orders from same restaurant
//        order.setRestaurant(cart.getCartItems().get(0).getMenuItem().getMenu().getRestaurant());

        CartItem firstItem = cart.getItems().stream().findFirst().orElseThrow(()->new RuntimeException("cart is empty"));
        Restaurant restaurant = firstItem.getMenuItem().getMenu().getRestaurant();
        order.setRestaurant(restaurant);

        //set order status to
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatusName("created");

        order.setOrderStatus(orderStatus);

        //transfer cart items to order items
        transferCartToOrder(cart, order);

        order.setDistinctItemCount(cart.getItems().size());

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        int totalItemQuantity = 0;

        for(OrderItem item:order.getItems())
        {
            totalPrice = totalPrice.add(item.getPrice());
            totalItemQuantity += item.getQuantity();
        }
        order.setTotalItemQuantity(totalItemQuantity);
        order.setTotalPrice(totalPrice);

        //save the order
        orderRepository.save(order);

        //empty cart
        cartService.clearCart(cart);

        //notify user that order created successfully
        System.out.println("user notification successful");

        return order;
    }

    public List<Order> getOrderHistoryForCustomer(Long customerId) {
    	
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrderHistoryForRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }
    
    
    public Order comleteOrder(Long orderId) {
		
		Order order= orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));
		
		OrderStatus orderStatus=order.getOrderStatus();
		orderStatus.setStatusName("completed");

		return order;
	}
    
    private void transferCartToOrder(Cart cart , Order order) {

        List<CartItem> itemList = cart.getItems();
        if(itemList.isEmpty())throw new RuntimeException("cart is empty");

        menuItemService.reduceInventory(itemList);

        for(CartItem item: itemList)
        {
            OrderItem orderItem = OrderItem
                    .builder()
                    .menuItem(item.getMenuItem())
                    .quantity(item.getQuantity())
                    .order(order)
                    .price(item.getMenuItem().getPrice())
                    .build();

            order.addItems(orderItem);
        }
    }


    //Cancel Order
    @Transactional
    public boolean cancelOrder(Long orderId) {
        // Retrieve the order from the database
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.ORDER_NOT_FOUND));

        // Check if the order is already canceled or completed or confirmed
        if (order.getOrderStatus().getId() ==2
                || order.getOrderStatus().getId() ==3
                || order.getOrderStatus().getId() ==4) {
            return false;
        }
        else {
            //Fetch cancel status
            OrderStatus canceledStatus = orderStatusRepository.findById(3L)
                    .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.CANCELED_STATUS_NOT_FOUND));

            // Update order status to "Canceled" in the order table
            order.setOrderStatus(canceledStatus);
            orderRepository.save(order);

            // Handle order tracking, Insert new order tracking record with new order status "Canceled")
            OrderTracking orderTracking = new OrderTracking(null,order,canceledStatus,LocalDateTime.now(),"Restaurant");
            orderTrackingRepository.save(orderTracking);
        }
        return true;
    }
}
