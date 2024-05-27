package com.trip.item.service;

import com.trip.item.model.ItemDto;
import com.trip.item.model.dto.OrderDto;
import java.io.IOException;
import java.util.List;

public interface ItemService {

    List<ItemDto> getItems() throws IOException;
    ItemDto getItemById(int itemId) throws IOException;
    void createOrder(int itemId, int memberId, int quantity);
    List<OrderDto> getAllOrdersByMemberId(int memberId);
    OrderDto getOrderById(int orderId);
}
