package food_delivery.dto;

import food_delivery.model.Restaurant;
import lombok.Data;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    public RestaurantDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.latitude = restaurant.getLocation().getY();
        this.longitude = restaurant.getLocation().getX();
    }

    // Getters and setters
}
