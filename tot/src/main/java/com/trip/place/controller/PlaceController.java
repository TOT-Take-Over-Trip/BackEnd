package com.trip.place.controller;

import com.trip.place.model.PlaceDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {


    @GetMapping
    public List<PlaceDto> getPlaces() {
        List<PlaceDto> places = new ArrayList<PlaceDto>();
        return places;
    }

    @GetMapping
    public PlaceDto getPlace(@PathVariable(value = "id") Long placeId) {
        PlaceDto place = new PlaceDto();
        return place;
    }

    @PostMapping
    public void addPlace(@RequestBody PlaceDto place) {

    }
}
