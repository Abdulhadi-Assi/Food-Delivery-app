package food_delivery.service.impl;

import org.springframework.stereotype.Service;
import food_delivery.model.RestaurantDetails;
import food_delivery.repository.RestaurantDeatailsRepository;
import food_delivery.request.RestaurantRequest;
import food_delivery.service.RestaurantDetailsService;
import food_delivery.request.RestaurantDetailsRequest;
import java.util.Optional;


@Service
public class RestaurantDetailsServiceImpl implements RestaurantDetailsService {

	private RestaurantDeatailsRepository restaurantDeatailsRepository;

    @Override
    public RestaurantDetails getRestaurantDetails(Long id) {
        return null;
    }

	@Override
	public RestaurantDetails UpdateRestaurantDetails(RestaurantDetails restaurantDetails, RestaurantDetailsRequest restaurantDetailsRequest) {

		if(restaurantDetailsRequest == null)
		{
			return restaurantDetails;
		}

		Optional.ofNullable(restaurantDetailsRequest.getDescription()).ifPresent(restaurantDetails::setDescription);
		Optional.ofNullable(restaurantDetailsRequest.getCapacity()).ifPresent(restaurantDetails::setCapacity);

		return restaurantDeatailsRepository.save(restaurantDetails);
	}

	@Override
	public RestaurantDetails createRestaurantDetails(RestaurantRequest req) {

		RestaurantDetails restaurantDetails = new RestaurantDetails();

		restaurantDetails.setDescription(req.getDescription());
		restaurantDetails.setCapacity(req.getCapacity());

		return restaurantDeatailsRepository.save(restaurantDetails);
	}
}
