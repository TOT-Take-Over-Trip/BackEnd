package com.trip.item.service;

import com.trip.item.model.ItemDto;
import com.trip.item.model.dto.OrderDto;
import java.util.List;

public interface ItemService {

    List<ItemDto> getItems();
    ItemDto getItemById(int itemId);
    void createOrder(int itemId, int memberId, int quantity);
    List<OrderDto> getAllOrdersByMemberId(int memberId);
    OrderDto getOrderById(int orderId);
}
