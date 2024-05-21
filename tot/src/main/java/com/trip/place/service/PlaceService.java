package com.trip.place.service;

import com.trip.place.model.PlaceDto;
import java.util.List;
import org.springframework.stereotype.Service;

public interface PlaceService {
    void csvDataInsert();
    List<PlaceDto> getPlaces();
    PlaceDto getPlaceById(int placeId);
    void insertPlace(PlaceDto placeDto);
    List<PlaceDto> getByKeyword(String keyword);
}
