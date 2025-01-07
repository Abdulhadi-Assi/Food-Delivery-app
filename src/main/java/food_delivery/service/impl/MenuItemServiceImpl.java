package food_delivery.service.impl;

import food_delivery.dto.RestaurantOwnerDTO;
import food_delivery.exception.ApplicationErrorEnum;
import food_delivery.exception.BusinessException;
import food_delivery.model.CartItem;
import food_delivery.model.MenuItem;
import food_delivery.repository.MenuItemRepository;
import food_delivery.request.MenuItemRequest;
import food_delivery.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional
    public void reduceInventory(List<CartItem> itemList) {
        itemList.forEach(cartItem -> {
            Long id = cartItem.getMenuItem().getMenuItemId();
            MenuItem menuItem = menuItemRepository.findById(id)
                    .orElseThrow(()->new BusinessException(ApplicationErrorEnum.MENU_ITEM_NOT_FOUND));

            if(menuItem.getQuantity() < cartItem.getQuantity()) {
                throw new BusinessException(ApplicationErrorEnum.LOW_INVENTORY);
//                throw new RuntimeException("not enough inventory for item with id: "+ menuItem.getMenuItemId());
            }
            menuItem.setQuantity(menuItem.getQuantity() - cartItem.getQuantity());
            menuItemRepository.save(menuItem);
        });
    }

    @Override
    public MenuItem getMenuItemById(Long id,Long userId) {
        MenuItem menuItem =  menuItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.MENU_ITEM_NOT_FOUND));

        RestaurantOwnerDTO restaurantOwnerDTO = menuItemRepository.findRestaurantOwnerIdByMenuItemId(id);

        Long restaurantOwnerId = null;
        if(restaurantOwnerDTO != null && restaurantOwnerDTO.getOwnerId() != null)
            restaurantOwnerId = restaurantOwnerDTO.getOwnerId();


        if(menuItem.getQuantity() == 0 && !userId.equals(restaurantOwnerId))
            throw new BusinessException(ApplicationErrorEnum.MENU_ITEM_NOT_FOUND);

        //user doesn't need to know quantity available in inventory
        if(!userId.equals(restaurantOwnerId))
            menuItem.setQuantity(null);

        return menuItem;
    }
    @Override
    public List<MenuItem> getMenuItemsByMenuId(Long menuId) {
        return menuItemRepository.findByMenuId(menuId);
    }

    @Override
    public void deleteMenuItemById(Long id) {
        menuItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.MENU_ITEM_NOT_FOUND));
        menuItemRepository.deleteById(id);
    }

	@Override
	public void updateMenuItem(Long menuItemId, MenuItemRequest menuItemRequest) {
		MenuItem existingMenuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.MENU_ITEM_NOT_FOUND));

        existingMenuItem.setItemName(menuItemRequest.getItemName());
        existingMenuItem.setPrice(menuItemRequest.getPrice());
        existingMenuItem.setDescription(menuItemRequest.getDescription());
        existingMenuItem.setQuantity(menuItemRequest.getQuantity());
        menuItemRepository.save(existingMenuItem);
		
	}
}
