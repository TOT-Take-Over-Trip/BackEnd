package com.trip.place.model.mapper;

import com.trip.place.model.PlaceDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceMapper {

    List<PlaceDto> selectAll();
    PlaceDto selectById(Long id);
    void insertPlace(PlaceDto place);
    PlaceDto selectPlace(String name);

}
