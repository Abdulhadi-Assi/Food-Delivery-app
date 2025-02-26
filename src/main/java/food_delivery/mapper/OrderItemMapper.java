package food_delivery.mapper;

import food_delivery.dto.OrderItemDTO;
import food_delivery.model.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toDto(OrderItem orderItem)
    {
        OrderItemDTO orderItemDTO = OrderItemDTO
                .builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();

        return orderItemDTO;
    }
}
