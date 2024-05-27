package com.trip.place.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceDto {
    private int placeId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int sidoCode;
    private int gugunCode;
    private String thumbnail;
    private String content;
    private String createdDate;
    private String updatedDate;
    private String status;

    @Builder
    public PlaceDto(String name, String address, double latitude, double longitude, int sidoCode,
        int gugunCode, String thumbnail, String content) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sidoCode = sidoCode;
        this.gugunCode = gugunCode;
        this.thumbnail = thumbnail;
        this.content = content;
    }

    @Override
    public String toString() {
        return "PlaceDto{" +
            "placeId=" + placeId +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", sidoCode=" + sidoCode +
            ", gugunCode=" + gugunCode +
            ", thumbnail='" + thumbnail + '\'' +
            ", content='" + content + '\'' +
            ", createdDate='" + createdDate + '\'' +
            ", updatedDate='" + updatedDate + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
