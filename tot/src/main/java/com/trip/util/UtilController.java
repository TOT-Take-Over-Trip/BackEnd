package com.trip.util;

import com.trip.course.model.dto.CourseResponseDto;
import com.trip.course.service.CourseService;
import com.trip.member.model.MemberDto;
import com.trip.member.service.MemberService;
import com.trip.notification.model.mapper.NotificationMapper;
import com.trip.post.model.dto.PostResponseDto;
import com.trip.post.service.PostService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/util")
@CrossOrigin("*")
public class UtilController {

    private final MemberService memberService;
    private final PostService postService;
    private final CourseService courseService;
    private final NotificationMapper notificationMapper;

    /**
     * 원래는 API가 아닌 자체적으로 처리하는게 맞지만 시연용으로 만듬
     */
    @PostMapping("/point")
    public void givePoint() throws IOException {
        List<MemberDto> members = memberService.getMembers();   //모든 멤버 조회
        for (MemberDto member : members) {
            int memberId = member.getMemberId();
            List<PostResponseDto> postsByMemberId = postService.getPostsByMemberId(memberId);   //멤버가 가지고 있는 게시글 조회
            List<CourseResponseDto> coursesByMemberId = courseService.getCourseByMemberId(memberId);

            int postPoint = 0;  //게시글로 얻는 포인트
            int coursePoint = 0; //코스로 얻는 포인트
            for(PostResponseDto post : postsByMemberId){
                postPoint+= post.getPostLikeCount();    //게시글의 좋아요 개수만큼 포인트 지급
            }
            for(CourseResponseDto course : coursesByMemberId){
                coursePoint+= course.getCourseLikeCount();    //코스의 좋아요 개수만큼 포인트 지급
                coursePoint+= course.getHit()/10; //조회수의 10분의 1만큼 포인트 지급
            }
            HashMap<String, Integer> pointMap = new HashMap<>();
            pointMap.put("memberId", memberId);
            pointMap.put("price", postPoint+coursePoint);
            memberService.updatePoint(pointMap);

            HashMap<String, Object> map = new HashMap<>();
            map.put("memberId", memberId);
            String content = "Daily 지급 포인트: " + "[게시글로 얻은 포인트: "+ postPoint + "포인트] & [코스로 얻은 포인트: " + coursePoint + "]";
            map.put("content", content);
            notificationMapper.insertNotification(map);
        }
    }
}
