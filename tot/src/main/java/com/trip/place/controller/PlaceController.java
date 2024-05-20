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
        return placeService.getPlaces();
    }

    @GetMapping("/{placeId}")
    public PlaceDto getPlace(@PathVariable int placeId) {
        return placeService.getPlaceById(placeId);
    }

    @PostMapping
    public void addPlace(@RequestBody PlaceDto place) {
        placeService.insertPlace(place);
    }
}
