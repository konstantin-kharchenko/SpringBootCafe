package by.kharchenko.springbootcafe.model.mapper;

import by.kharchenko.springbootcafe.model.Order;
import by.kharchenko.springbootcafe.model.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO OrderToOrderDTO(Order order);

    Order OrderDTOToOrder(OrderDTO orderDTO);
}
