package com.trip.item.service;

import com.trip.item.model.ItemDto;
import com.trip.item.model.dto.OrderDto;
import com.trip.item.model.mapper.ItemMapper;
import com.trip.member.model.MemberDto;
import com.trip.member.model.mapper.MemberMapper;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemMapper itemMapper;
    private final MemberMapper memberMapper;

    @Override
    public List<ItemDto> getItems() {
        return itemMapper.selectAllItems();
    }

    @Override
    public ItemDto getItemById(int itemId) {
        return itemMapper.selectItemById(itemId);
    }

    //TODO: 아래 과정은 하나의 트랜잭션에서 일어나야한다.
    @Override
    public void createOrder(OrderDto orderDto) {
        //아이템 수량 감소
        ItemDto item = itemMapper.selectItemById(orderDto.getItemId());
        if(item.getQuantity()>= orderDto.getQuantity()){
            HashMap<String, Integer> map = new HashMap<>();
            map.put("itemId", orderDto.getItemId());
            map.put("quantity", orderDto.getQuantity());
            itemMapper.updateQuantity(map);
        }

        //멤버 point 감소
        MemberDto member = memberMapper.selectMemberById(orderDto.getMemberId());
        if(member.getPoint()>=orderDto.getPrice()) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("memberId", orderDto.getMemberId());
            map.put("price", orderDto.getPrice());
            memberMapper.updatePoint(map);
        }

        //order_history에 주문 내역 저장
        itemMapper.insertOrder(orderDto);
    }

    @Override
    public List<OrderDto> getAllOrdersByMemberId(int memberId) {
        return itemMapper.selectAllOrdersByMemberId(memberId);
    }

    @Override
    public OrderDto getOrderById(int orderId) {
        return itemMapper.selectOrderById(orderId);
    }
}
