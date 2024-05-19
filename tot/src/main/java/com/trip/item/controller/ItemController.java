package com.trip.item.controller;

import com.trip.item.model.ItemDto;
import com.trip.item.model.dto.OrderDto;
import com.trip.item.service.ItemService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getItems() throws IOException {
        return itemService.getItems();
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable(value = "itemId") int itemId) throws IOException {
        return itemService.getItemById(itemId);
    }

    @PostMapping("/orders")
    public void order(@RequestParam("itemId") int itemId,
        @RequestParam("memberId") int memberId, @RequestParam("quantity") int quantity){
        itemService.createOrder(itemId, memberId, quantity);
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders(@RequestParam("memberId") int memberId){
        return itemService.getAllOrdersByMemberId(memberId);
    }

    @GetMapping("/orders/{orderId}")
    public OrderDto getOrderById(@PathVariable int orderId){
        return itemService.getOrderById(orderId);
    }
}
