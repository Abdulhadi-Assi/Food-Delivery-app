package food_delivery.service.impl;

import food_delivery.exception.ApplicationErrorEnum;
import food_delivery.exception.BusinessException;
import food_delivery.model.*;
import food_delivery.repository.*;
import food_delivery.request.RestaurantRequest;
import food_delivery.service.AddressService;
import food_delivery.service.RestaurantDetailsService;
import food_delivery.request.UpdateRestaurantRequest;
import food_delivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {


	private final RestaurantRepository restaurantRepository;

	private final AddressService addressService;

	private final RestaurantDetailsService restaurantDetailsService;

	private final OrderRepository orderRepository;

	private final MenuRepository menuRepository;

	private final MenuItemRepository menuItemRepository;

	private final CartItemRepository cartItemRepository;

	@Override
	public void createRestaurant(RestaurantRequest req) {

		Address address =addressService.createAddress(req.getAddress());

		RestaurantDetails restaurantDetails  = restaurantDetailsService.createRestaurantDetails(req);

		Restaurant restaurant = new Restaurant();

		restaurant.setName(req.getName());
		restaurant.setPhoneNumber(req.getPhoneNumber());
		restaurant.setAddress(address);
		restaurant.setRestaurantDetails(restaurantDetails);

		restaurantRepository.save(restaurant);
	}

    @Override
	@Transactional
    public void deleteRestaurantById(Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new BusinessException(ApplicationErrorEnum.RESTAURANT_NOT_FOUND));

		// Check if restaurant already deleted
		if (restaurant.isDeleted())
			throw new BusinessException(ApplicationErrorEnum.RESTAURANT_ALREADY_DELETED);

		// Valid orders
		Optional<Order> lastOrder = orderRepository.findLastOrderByRestaurantId(restaurantId);
		if (lastOrder.isPresent()){
			OrderStatus statusId=lastOrder.get().getOrderStatus();

		}

		// Delete the restaurant itself(soft delete)
		restaurant.setDeleted(true);
		restaurantRepository.save(restaurant);

		// Delete related data
		for (Menu menu : restaurant.getMenus()){
			List<MenuItem> menuItems = menuItemRepository.findByMenuId(menu.getId());
			for (MenuItem menuItem : menuItems){
				cartItemRepository.deleteAllByMenuItemId(menuItem.getMenuItemId());
			}
			menuItemRepository.deleteAllMenuItemsByRestaurantId(menu.getId());
		}
		menuRepository.deleteAllMenusByRestaurantId(restaurantId);
    }

    @Override
    public void updateRestaurant(UpdateRestaurantRequest updateRestaurantRequest) {
		Restaurant restaurant = getRestaurant(updateRestaurantRequest.getId());

		Address updatedAddress =  addressService.updateAddress(restaurant.getAddress() , updateRestaurantRequest.getAddress());
		restaurant.setAddress(updatedAddress);

		RestaurantDetails updatedRestaurantDetails = restaurantDetailsService.updateRestaurantDetails(restaurant.getRestaurantDetails() , updateRestaurantRequest.getRestaurantDetails());
		restaurant.setRestaurantDetails(updatedRestaurantDetails);

		Optional.ofNullable(updateRestaurantRequest.getName()).ifPresent(restaurant::setName);
		Optional.ofNullable(updateRestaurantRequest.getPhoneNumber()).ifPresent(restaurant::setPhoneNumber);

        restaurantRepository.save(restaurant);
	}

	private Restaurant getRestaurant(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(()-> new BusinessException(ApplicationErrorEnum.RESTAURANT_NOT_FOUND));
	}
}
