package com.trip.item.controller;

import com.trip.item.model.ItemDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @GetMapping
    public List<ItemDto> getItems(){
        List<ItemDto> itemDtos = new ArrayList<>();
        return itemDtos;
    }

    @GetMapping("/id")
    public ItemDto getItem(@PathVariable(value = "id") Long itemId){
        ItemDto itemDto = new ItemDto();
        return itemDto;
    }
}
