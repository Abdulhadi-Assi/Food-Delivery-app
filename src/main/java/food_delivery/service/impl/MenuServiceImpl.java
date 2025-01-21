package food_delivery.service.impl;

import food_delivery.enumeration.ApplicationErrorEnum;
import food_delivery.exception.BusinessException;
import food_delivery.mapper.MenuResponseMapper;
import food_delivery.model.Menu;
import food_delivery.model.MenuItem;
import food_delivery.model.Restaurant;
import food_delivery.repository.MenuRepository;
import food_delivery.repository.RestaurantRepository;
import food_delivery.request.MenuItemRequest;
import food_delivery.request.MenuRequest;
import food_delivery.request.UpdateMenuRequest;
import food_delivery.response.MenuResponse;
import food_delivery.service.MenuItemService;
import food_delivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuItemService menuItemService ;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public MenuResponse createMenu(MenuRequest menuRequest){
        // Find the restaurant by ID
        Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId())
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.RESTAURANT_NOT_FOUND));

        // Create a new menu object
        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        menu.setMenuName(menuRequest.getMenuName());
        menu.setDescription(menuRequest.getDescription());

        // Save the menu
        menuRepository.save(menu);
        return new MenuResponse(menu.getId(),menu.getMenuName(),menu.getDescription(),
                                restaurant.getName(),null);
    }

    @Override
    public Optional<Menu> getMenuById(Long id) {
        return Optional.empty();
    }

    @Override
    public MenuResponse getMenuWithItems(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with ID: " + id));
        List<MenuItem> menuItems = menuItemService.getMenuItemsByMenuId(id);
        return MenuResponseMapper.toMenuResponse(menu, menuItems);
    }

    @Transactional
    @Override
    public void deleteMenuById(Long menuId) {
        Menu menu = getMenu(menuId);

        Long restaurantMenuCount = menuRepository.countByRestaurant_Id(menu.getRestaurant().getId());
        if(1L == restaurantMenuCount)
        {
            throw new RuntimeException("menu can not be deleted, restaurant should have one menu at least");
        }

        //sets the foreign key for each menu item to null
        menuItemService.setFkMenuIdToNull(menuId);

        menuRepository.delete(menu);
    }

    private Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(()->new BusinessException(ApplicationErrorEnum.MENU_NOT_FOUND));
    }

    @Transactional
	@Override
	public void updateMenu(Long menuId,UpdateMenuRequest menuRequest) {
    	Menu menu = menuRepository.findById(menuId).orElseThrow(()->new BusinessException(ApplicationErrorEnum.MENU_NOT_FOUND));
    	
    	if (menuRequest.getMenuName() != null && !menuRequest.getMenuName().isEmpty()) {
    		menu.setMenuName(menuRequest.getMenuName());
        }
    	if (menuRequest.getDescription() != null && !menuRequest.getDescription().isEmpty()) {
    		menu.setDescription(menuRequest.getDescription());
        }
    
        if (menuRequest.getMenuItemsItems() != null && !menuRequest.getMenuItemsItems().isEmpty()) {
            menuRequest.getMenuItemsItems().forEach(this::updateMenuItem);
        }
    	menuRepository.save(menu);
	}

	private void updateMenuItem(MenuItemRequest item) {
		 menuItemService.updateMenuItem(item.getMenuId(), item);
		
	}
}
