package food_delivery.repository;

import food_delivery.dto.RestaurantOwnerDTO;
import food_delivery.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.transaction.Transactional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{

    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.id = :menuId")
    List<MenuItem> findByMenuId(Long menuId);

    @Query("SELECT new food_delivery.dto.RestaurantOwnerDTO(o.id) FROM MenuItem m JOIN m.menu menu JOIN menu.restaurant r JOIN r.restaurantDetails d JOIN d.owner o WHERE m.id = :menuItemId")
    RestaurantOwnerDTO findRestaurantOwnerIdByMenuItemId(@Param("menuItemId") Long menuItemId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM menu_item WHERE menu_id IN (SELECT menu_id FROM menu WHERE restaurant_id = :restaurantId)", nativeQuery = true)
    void deleteAllMenuItemsByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Modifying
    @Query("UPDATE MenuItem m SET m.menu = null WHERE m.menu.id = :menuId")
    void setFkMenuIdToNull(@Param("menuId") Long menuId);
}
