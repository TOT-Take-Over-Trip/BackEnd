package com.trip.place.service;

import com.trip.place.model.PlaceDto;
import com.trip.place.model.mapper.PlaceMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

    private final PlaceMapper placeMapper;

    /**
     * 관광지 API 데이터 전처리 후 place DB에 넣기. 개발 이후 삭제할 예정
     */
    @Override
    public void csvDataInsert() {
        String csvFile = "/Users/dongyullee/ssafy/file/data.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                // CSV 파일의 각 행을 콤마로 분리 (데이터 콤마 있는 경우를 정규식으로 처리)
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

//                String content_id = data[0]; //content_id
//                String content_type_id = data[1]; //content_type_id
                if(data.length <=17){
                    System.out.println(data[0]);
                    continue;
                }
                if(placeMapper.selectPlace(data[2])!=null){
                    System.out.println(data[2]);
                    continue;
                }
                String name = data[2]; //name
                String address = data[3];
//                String addr2 = data[4];
//                String zipcode = data[5];
//                String tel = data[6];
                String thumbnail = data[7];
//                String img2 = data[8];
//                int readCount = Integer.parseInt(data[9]);
                int sidoCode = data[10].equals("")? -1: Integer.parseInt(data[10]);
                int gugunCode = data[11].equals("")? -1: Integer.parseInt(data[11]);

                double latitude = data[12].equals("")? -1: Double.parseDouble(data[12]);
                double longitude = data[13].equals("")? -1: Double.parseDouble(data[13]);
//                String mlevel = data[14];
//                String content_id = data[15];
//                String homepage = data[16];
                String content = data[17];

                PlaceDto place = PlaceDto.builder()
                    .name(name)
                    .address(address)
                    .thumbnail(thumbnail)
                    .sidoCode(sidoCode)
                    .gugunCode(gugunCode)
                    .latitude(latitude)
                    .longitude(longitude)
                    .content(content)
                    .build();

                placeMapper.insertPlace(place);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
