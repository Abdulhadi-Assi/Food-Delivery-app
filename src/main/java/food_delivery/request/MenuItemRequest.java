package food_delivery.request;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class MenuItemRequest {
	
    private Long menuItemId;
    private String itemName;
    private BigDecimal price;
    private String description;
    private Integer quantity;
}
