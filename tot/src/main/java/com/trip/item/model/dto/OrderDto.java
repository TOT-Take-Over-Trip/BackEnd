package com.trip.item.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private int orderId;
    private int itemId;
    private int memberId;
    private int price;
    private int quantity;
    private String createdDate;
    private String updatedDate;
    private String status;

}
