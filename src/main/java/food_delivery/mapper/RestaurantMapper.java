package food_delivery.mapper;

import food_delivery.model.Restaurant;
import food_delivery.request.AddressRequest;
import food_delivery.request.RestaurantDetailsRequest;
import food_delivery.response.RestaurantResponse;

public class RestaurantMapper {

    public static RestaurantResponse toRestaurantResponse(Restaurant restaurant)
    {
        if (restaurant == null)return null;

        RestaurantDetailsRequest restaurantDetailsRequest = new RestaurantDetailsRequest(restaurant.getRestaurantDetails().getDescription(),restaurant.getRestaurantDetails().getCapacity());

        AddressRequest addressRequest = AddressMapper.toAddressRequest(restaurant.getAddress());

        return new RestaurantResponse(restaurant.getId(),restaurant.getName(),restaurantDetailsRequest,addressRequest,restaurant.getPhoneNumber());
    }
}
