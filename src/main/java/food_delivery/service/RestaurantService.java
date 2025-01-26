package food_delivery.service;

import food_delivery.model.Restaurant;
import food_delivery.request.UpdateRestaurantRequest;
import food_delivery.request.RestaurantRequest;
import org.springframework.data.domain.Page;

public interface RestaurantService {

	void createRestaurant(RestaurantRequest req);
    void deleteRestaurantById(Long id);

    void updateRestaurant(UpdateRestaurantRequest updateRestaurantRequest);

    Restaurant getRestaurant(Long restaurantId);

    Page<Restaurant> searchRestaurants(
            String name,
            String description,
            Double latitude,
            Double longitude,
            Double radius,
            int page,
            int size
    );
}
