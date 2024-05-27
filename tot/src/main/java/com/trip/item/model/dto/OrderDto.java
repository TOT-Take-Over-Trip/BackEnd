package com.trip.item.model.dto;

import lombok.Builder;
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

    @Builder
    public OrderDto(int itemId, int memberId, int price, int quantity){
        this.itemId = itemId;
        this.memberId = memberId;
        this.price = price;
        this.quantity = quantity;
    }
}
