package food_delivery.specifications;

import food_delivery.model.Restaurant;
import food_delivery.model.RestaurantDetails;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class RestaurantSpecifications {
    public static Specification<Restaurant> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    // Search by description (optional)
    public static Specification<Restaurant> hasDescription(String description) {
        return (root, query, cb) -> {
            if (description == null || description.trim().isEmpty()) return null;
            Join<Restaurant, RestaurantDetails> detailsJoin = root.join("restaurantDetails", JoinType.LEFT); // Use LEFT join
            return cb.like(cb.lower(detailsJoin.get("description")), "%" + description.toLowerCase() + "%");
        };
    }

    // Filter by location (optional)
    public static Specification<Restaurant> withinRadius(Double latitude, Double longitude, Double radius) {
        return (root, query, cb) -> {
            if (latitude == null || longitude == null || radius == null) return null;

            Expression<Geometry> userPoint = cb.function(
                    "ST_SetSRID",
                    Geometry.class,
                    cb.function("ST_MakePoint", Geometry.class, cb.literal(longitude), cb.literal(latitude)),
                    cb.literal(4326)
            );

            Expression<Double> distance = cb.function(
                    "ST_Distance",
                    Double.class,
                    root.get("location"),
                    userPoint
            );

            return cb.lessThanOrEqualTo(distance, radius);
        };
    }
}