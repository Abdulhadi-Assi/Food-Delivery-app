package food_delivery.controller;

import food_delivery.mapper.RestaurantMapper;
import food_delivery.model.Restaurant;
import food_delivery.request.RestaurantRequest;
import food_delivery.request.UpdateRestaurantRequest;
import food_delivery.service.RestaurantService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RequiredArgsConstructor
@RequestMapping("api/v1/restaurant")
@RestController
public class RestaurantController {
    private final RestaurantService restaurantService ;
    
    @PostMapping("")
    public ResponseEntity<Void> createRestaurant(@RequestBody RestaurantRequest restaurantRequest)
    {
         restaurantService.createRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<?> updateRestaurant(
            @RequestBody UpdateRestaurantRequest updateRestaurantRequest
    )
    {
        restaurantService.updateRestaurant(updateRestaurantRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id){
        restaurantService.deleteRestaurantById(id);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getRestaurant(@PathVariable Long restaurantId)
    {
        Restaurant restaurant =  restaurantService.getRestaurant(restaurantId);
        return ResponseEntity.ok().body(RestaurantMapper.toRestaurantResponse(restaurant));
    }

}

   
