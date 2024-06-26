package com.trip.course.service;


import com.trip.config.RedisUtil;
import com.trip.course.model.CourseDto;
import com.trip.course.model.dto.CoursePlaceDto;
import com.trip.course.model.dto.CourseResponseDto;
import com.trip.course.model.dto.CoursesResponseDto;
import com.trip.course.model.mapper.CourseMapper;
import com.trip.member.model.MemberDto;
import com.trip.member.model.mapper.MemberMapper;
import com.trip.notification.model.mapper.NotificationMapper;
import com.trip.place.model.mapper.PlaceMapper;
import com.trip.post.model.dto.PostResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final MemberMapper memberMapper;
    private final PlaceMapper placeMapper;
    private final NotificationMapper notificationMapper;
    private final RedisUtil redisUtil;

    //모든 코스 조회
    @Override
    public CoursesResponseDto getAllCourses(int memberId) {
        CoursesResponseDto coursesResponseDto = new CoursesResponseDto();
        List<CourseResponseDto> courses = courseMapper.selectAllCourses(memberId);

        //정렬
        Collections.sort(courses, new Comparator<CourseResponseDto>() {
            @Override
            public int compare(CourseResponseDto o1, CourseResponseDto o2) {
                return o2.getCourseLikeCount() - o1.getCourseLikeCount();
            }
        });
        //courses
        for(CourseResponseDto course : courses){
            List<CoursePlaceDto> coursePlaces = getCoursePlaces(course.getCourseId());
            for(CoursePlaceDto coursePlaceDto : coursePlaces){
                coursePlaceDto.setPlace(placeMapper.selectById(coursePlaceDto.getPlaceId()));
            }
            course.setCoursePlaces(coursePlaces);
        }

        //topRankCourse
        List<CourseResponseDto> topRankCourses = new ArrayList<>(); //좋아요 많은 데이터
        int index = 0;
        for(CourseResponseDto course : courses){
            //30일 이상 지난 데이터를 사용하지 않기 위한 로직
            String date = course.getUpdatedDate().split(" ")[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            LocalDate today = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(parsedDate, today);
            if(daysBetween<30){
                topRankCourses.add(course);
                index++;
            }
            //10개 이상 넘으면 탈출
            if(index>=5) {
                break;
            }
        }
        coursesResponseDto.setCourses(courses);
        coursesResponseDto.setTopRankCourses(topRankCourses);
        return coursesResponseDto;
    }

    //단일 코스 조회
    @Override
    public CourseResponseDto getCourseById(int courseId, int memberId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        CourseResponseDto course = courseMapper.selectCourseById(map);
        if(memberId!=course.getMemberId()) {
            updateHit(courseId,memberId); //내가 만든 코스가 아니면 조회수 증가(이미 조회했으면 증가 x)
        }
        List<CoursePlaceDto> coursePlaces = getCoursePlaces(course.getCourseId());
        for(CoursePlaceDto coursePlaceDto : coursePlaces){
            coursePlaceDto.setPlace(placeMapper.selectById(coursePlaceDto.getPlaceId()));
        }
        Collections.sort(coursePlaces, new Comparator<CoursePlaceDto>() {
            @Override
            public int compare(CoursePlaceDto o1, CoursePlaceDto o2) {
                return o1.getSequence() - o2.getSequence();
            }
        });
        course.setCoursePlaces(coursePlaces);
        return course;
    }

    //어떤 코스의 세부정보(장소 + 내용) 조회
    @Override
    public List<CoursePlaceDto> getCoursePlaces(int courseId) {
        return courseMapper.selectCoursePlaces(courseId);
    }

    //코스의 세부정보 단일조회
    @Override
    public CoursePlaceDto getCoursePlaceById(int coursePlaceId) {
        return courseMapper.selectCoursePlaceById(coursePlaceId);
    }

    @Override
    public void addLike(int courseId, int memberId) {
        HashMap<String, Object> map =new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        courseMapper.addLike(map);
        courseMapper.addLikeCount(courseId);
    }

    @Override
    public void cancelLike(int courseId, int memberId) {
        HashMap<String, Object> map =new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        courseMapper.cancelLike(map);
        courseMapper.cancelLikeCount(courseId);
    }

    //코스 세부정보 넣기
    @Override
    public void insertCoursePlace(int courseId, CoursePlaceDto coursePlace) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("placeId", coursePlace.getPlaceId());
        map.put("content", coursePlace.getContent());
        map.put("sequence", coursePlace.getSequence());
        courseMapper.insertCoursePlace(map);
    }

    //코스 등록
    @Override
    public void insertCourse(CourseDto course) {
        courseMapper.insertCourse(course); //코스 생성되고 키를 course에 저장
        int courseId = course.getCourseId();
        for (CoursePlaceDto coursePlace : course.getCoursePlaces()) {
            insertCoursePlace(courseId, coursePlace);   //코스 장소들 넣어주기
        }
    }

    //코스 수정
    @Override
    public void modifyCourse(int courseId, CourseDto course) {
        courseMapper.deleteCoursePlaceByCourseId(courseId); //기존에 저장되어있는 coursePlace 전부 삭제
        course.setCourseId(courseId);
        for (CoursePlaceDto coursePlace : course.getCoursePlaces()) {
            insertCoursePlace(courseId, coursePlace);   //코스 장소들 넣어주기
        }
        courseMapper.modifyCourse(course);
    }

    //코스 인수
    @Override
    public void takeOverCourse(int courseId, int memberId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        CourseResponseDto course = getCourseById(courseId, memberId);
        //원래 주인
        int originalMemberId = course.getMemberId();
        MemberDto member = memberMapper.selectMemberById(memberId);
        //코스 가격
        int coursePrice = course.getCourseLikeCount()*10 + course.getHit();
        if(coursePrice<member.getPoint()) {
            courseMapper.takeOverCourse(map);
            HashMap<String, Object> notificationMap = new HashMap<>();
            notificationMap.put("memberId", originalMemberId);
            String content =
                "<span style='color: blue;'>" + course.getTitle() + " 코스가" + "</span><br>"
                    + "<span style='color: red;'>" + member.getId() + "</span>" + "님에게 인수되었습니다!<br>"
            +"획득한 포인트: " + "<span style='color: blue;'>" + coursePrice/2 +"P</span>";
            notificationMap.put("content", content);
            notificationMapper.insertNotification(notificationMap);
            //구매자 돈 감소
            HashMap<String, Integer> pointMap = new HashMap<>();
            pointMap.put("memberId", memberId);
            pointMap.put("price", -coursePrice);
            memberMapper.updatePoint(pointMap);

            //판매자 돈 증가
            HashMap<String, Integer> pointMap2 = new HashMap<>();
            pointMap2.put("memberId", originalMemberId);
            pointMap2.put("price", coursePrice/2);
            memberMapper.updatePoint(pointMap2);
        }
    }

    //코스 삭제
    @Override
    public void deleteCourse(CourseDto course) {
        courseMapper.deleteCourse(course);
    }

    //특정 회원이 가지고 있는 코스 전부 조회
    @Override
    public List<CourseResponseDto> getCourseByMemberId(int memberId) {
        List<CourseResponseDto> courses = courseMapper.getCoursesByMemberId(memberId);
        for(CourseResponseDto course : courses){
            List<CoursePlaceDto> coursePlaces = getCoursePlaces(course.getCourseId());
            for(CoursePlaceDto coursePlaceDto : coursePlaces){
                coursePlaceDto.setPlace(placeMapper.selectById(coursePlaceDto.getPlaceId()));
            }
            course.setCoursePlaces(coursePlaces);
        }
        return courses;
    }

    /**
     * 어떤 유저가 해당 코스를 이미 조회했을 경우 조회수를 높이지 않도록 Redis를 이용하여 중복검사 진행
     */
    @Override
    public void updateHit(int courseId, int memberId) {
        String memberViewList = redisUtil.getData(String.valueOf(memberId));
        //조회되는게 없는 경우 예외처리후 redis 저장 및 조회수 증가
        if (memberViewList == null) {
            //value값 구분을 위해 - 넣어주기
            redisUtil.setDateExpire(String.valueOf(memberId), courseId + "-",
                calculateTimeUntilMidnight());
            courseMapper.updateHit(courseId);
            return;
        }

        String[] redisValueArray = memberViewList.split("-");   //해당 member가 이미 조회한 courseId목록
        boolean isView = false; //member가 해당코스를 오늘 조회했는지
        for (int i = 0; i < redisValueArray.length; i++) {
            if(redisValueArray[i].equals(String.valueOf(courseId))){
                isView = true;
                break;
            }
        }
        if(!isView){
            redisUtil.setDateExpire(String.valueOf(memberId), memberViewList+courseId + "-",
                calculateTimeUntilMidnight());
            courseMapper.updateHit(courseId);
        }
    }

    //자정까지의 시간 계산
    public static long calculateTimeUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.truncatedTo(ChronoUnit.DAYS).plusDays(1);
        return ChronoUnit.SECONDS.between(now, midnight);
    }
}
