package food_delivery.service;

import food_delivery.model.Menu;
import food_delivery.request.MenuRequest;
import food_delivery.request.UpdateMenuRequest;
import food_delivery.response.MenuResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MenuService {
    MenuResponse createMenu(MenuRequest menuRequest);

    Optional<Menu> getMenuById(Long id);

    MenuResponse getMenuWithItems(Long id);

    @Transactional
    public void deleteMenuById(Long menuId);

    void updateMenu(Long menuId, UpdateMenuRequest menuRequest);

}
