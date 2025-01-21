package food_delivery.mapper;

import food_delivery.dto.OrderDto;
import food_delivery.dto.OrderItemDTO;
import food_delivery.model.Order;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDto toDto(Order order)
    {
        List <OrderItemDTO> orderItemDTOS =
                order.getItems()
                        .stream()
                        .map(OrderItemMapper::toDto)
                        .collect(Collectors.toList());

        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .totalItemQuantity(order.getTotalItemQuantity())
                .distinctItemCount(order.getDistinctItemCount())
                .totalPrice(order.getTotalPrice())
                .items(orderItemDTOS)
                .build();

        return orderDto;
    }
}
