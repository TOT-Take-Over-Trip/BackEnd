package com.trip.item.model.mapper;

import com.trip.item.model.ItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    List<ItemDto> selectAll();
    ItemDto selectById(int id);
}
