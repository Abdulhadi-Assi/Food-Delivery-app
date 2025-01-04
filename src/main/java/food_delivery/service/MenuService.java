package food_delivery.service;

import food_delivery.model.Menu;
import food_delivery.response.MenuResponse;

import java.util.Optional;

public interface MenuService {
    Optional<Menu> getMenuById(Long id); // Fetch Menu by ID
    MenuResponse getMenuWithItems(Long id);
    public void deleteMenu(Long menuId);

}
