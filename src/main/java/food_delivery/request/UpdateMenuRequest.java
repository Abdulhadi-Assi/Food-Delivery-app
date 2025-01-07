package food_delivery.request;

import java.util.List;

import lombok.Data;

@Data
public class UpdateMenuRequest {
	
	    
	    private String menuName;
	    private String description;
	    
	    List<MenuItemRequest> menuItemsItems;

}
