package com.trip.item.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    private int itemId;
    private String name;
    private String itemImg;
    private int price;
    private int quantity;
    private String createdDate;
    private String updatedDate;
    private String status;

}
