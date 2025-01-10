package food_delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food_delivery.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>{

    Long countByRestaurant_Id(Long id);
}
