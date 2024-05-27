package com.trip.item.model.mapper;

import com.trip.item.model.ItemDto;
import com.trip.item.model.dto.OrderDto;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    List<ItemDto> selectAllItems();
    ItemDto selectItemById(int itemId);
    void updateQuantity(HashMap<String, Integer> map);
    void insertOrder(OrderDto orderDto);
    List<OrderDto> selectAllOrdersByMemberId(int memberId);
    OrderDto selectOrderById(int orderId);
}
