package food_delivery.response;

import food_delivery.request.AddressRequest;
import food_delivery.request.RestaurantDetailsRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestaurantResponse {
    private Long id;

    private String name;

    private RestaurantDetailsRequest restaurantDetails;

    private AddressRequest address;

    private String phoneNumber;
}
