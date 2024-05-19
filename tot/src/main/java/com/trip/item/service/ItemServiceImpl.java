package com.trip.item.service;

import com.trip.item.model.ItemDto;
import com.trip.item.model.dto.OrderDto;
import com.trip.item.model.mapper.ItemMapper;
import com.trip.member.model.MemberDto;
import com.trip.member.model.mapper.MemberMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemMapper itemMapper;
    private final MemberMapper memberMapper;

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<ItemDto> getItems() throws IOException {
        List<ItemDto> items = itemMapper.selectAllItems();
        for(ItemDto item : items){
            String filePath = item.getItemImg();
            byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
            String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
            item.setItemImg(base64EncodedString); //인코딩 정보 넣어주기
        }
        return items;
    }

    @Override
    public ItemDto getItemById(int itemId) throws IOException {
        ItemDto item = itemMapper.selectItemById(itemId);
        String filePath = item.getItemImg();
        byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
        String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
        item.setItemImg(base64EncodedString); //인코딩 정보 넣어주기
        return item;
    }

    //아래 과정이 하나의 트랜잭션에서 일어나야한다 -> Transactional 사용
    @Override
    @Transactional
    public void createOrder(int itemId, int memberId, int quantity) {
        //아이템 수량 감소
        ItemDto item = itemMapper.selectItemById(itemId);
        if(item.getQuantity()>= quantity){
            HashMap<String, Integer> map = new HashMap<>();
            map.put("itemId", itemId);
            map.put("quantity", quantity);
            itemMapper.updateQuantity(map);
        }else{
            throw new RuntimeException("아이템 수량이 부족합니다");
        }

        //멤버 point 감소
        int orderPrice = item.getPrice() * quantity;
        MemberDto member = memberMapper.selectMemberById(memberId);
        if(member.getPoint()>=item.getPrice()) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("memberId", memberId);
            map.put("price", orderPrice);
            memberMapper.updatePoint(map);
        }else{
            throw new RuntimeException("멤버 포인트가 부족합니다.");
        }

        OrderDto orderDto = OrderDto.builder()
            .itemId(itemId)
            .memberId(memberId)
            .price(orderPrice)
            .quantity(quantity)
            .build();

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
