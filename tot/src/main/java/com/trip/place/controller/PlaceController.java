package com.trip.place.controller;

import com.trip.place.model.PlaceDto;
import com.trip.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
@CrossOrigin("*")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/csv")
    public void addCsvData(){
        placeService.csvDataInsert();
    }

    @GetMapping
    public List<PlaceDto> getPlaces() {
        List<PlaceDto> places = new ArrayList<PlaceDto>();
        return places;
    }

    @GetMapping("/{id}")
    public PlaceDto getPlace(@PathVariable(value = "id") Long placeId) {
        PlaceDto place = new PlaceDto();
        return place;
    }

    @PostMapping
    public void addPlace(@RequestBody PlaceDto place) {

    }
}
