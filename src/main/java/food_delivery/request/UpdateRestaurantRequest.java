package food_delivery.request;

import lombok.Getter;
import javax.validation.constraints.*;

@Getter
public class UpdateRestaurantRequest {

    @NotNull(message = "id can not be null")
    @Positive(message = "id should be positive")
    private Long id;

    @NotBlank(message = "name can not be blank")
    private String name;

    private String phoneNumber;

    private RestaurantDetailsRequest restaurantDetails;

    private AddressRequest address;
}
