# ✈️ TOT

> **리뷰를 등록하고 코스를 인수하세요!**
> 

# 🧑🏻‍💻 프로젝트 및 멤버

> 기간: 2024.5.16 ~ 2024.5.23
> 

> 멤버 구성: 2명(Back-end & Front-end)
> 

## 프로젝트 소개

여행 정보 플랫폼 **TOT(Take Over Trip)은** 사용자들이 작성한 여행코스와 리뷰를 제공합니다. 글을 작성하여 포인트를 획득하고, 마음에 드는 코스가 있다면 코스를 인수하여 더 많은 포인트를 획득할 수 있습니다!

- 여행, 맛집등 리뷰를 작성하여 조회수와 좋아요를 얻어보아요!
- 지도를 활용하여 여행코스를 등록하고  조회수와 좋아요를 얻어보아요!
- 마음에 드는 코스가 있다면, 코스를 인수할 수 있습니다! 물론 나의 코스도 인수당할 수 있다는 사실!
- 매일 자신이 작성한 리뷰, 가지고 있는 코스의 조회수, 좋아요 개수에 따라 포인트를 획득할 수 있습니다!
- 이를 통해 획득한 포인트는 상품으로 교환할 수 있어요.

> **[기술 스택](#기술-스택)**
> 
> **[서비스 시연영상](#서비스-시연-영상)**
> 
> **[Trouble Shooting 내역](#trouble-shooting)**
> 
---

## 기술 스택

### Frontend
<div>
<img src="https://img.shields.io/badge/Vue.js-4FC08D?style=flat-square&logo=Vue.js&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/>
<img src="https://img.shields.io/badge/Tailwind CSS-06B6D4?style=flat-square&logo=Tailwind CSS&logoColor=white"/>
</div>

### Backend
![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
<img src="https://img.shields.io/badge/MyBatis-000000?style=for-the-badge&logo=MyBatis&logoColor=white"> 
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"> 
![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### 협업 툴
![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white)

---

## ERD

<img width="1187" alt="erd" src="https://github.com/TOT-Take-Over-Trip/BackEnd/assets/75566606/eb4be423-1d5c-4e4a-bbda-33af3aa9814d">

---

## 서비스 시연 영상

<img src="https://github.com/TOT-Take-Over-Trip/BackEnd/assets/75566606/37859fd8-4598-4ca5-811a-45b19a6ed2b1"/>  

<p align="center"> <a href="https://youtu.be/pZl-HfEG6f4?si=88JfUNoFrUAwjAqw" style="font-size:20em;">시연영상 유튜브 링크</a> </p>

---

## Trouble Shooting

### 조회수 증복 증가 문제

- **문제상황**: 사용자가 새로고침을 통해 같은 게시글, 코스에 대해 조회수를 무한으로 증가시킬 수 있는 문제가 발생. 조회수 기반으로 포인트를 지급하기 때문에 이는 반드시 막아야하는 상황.
- **해결방안**: Redis를 사용한 조회수 중복방지 로직 구현.
- **관련커밋**: [https://github.com/TOT-Take-Over-Trip/BackEnd/commit/883105d470e594fc2228ae2324c31e0be280a0c3]

```java
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
```

- “-”를 사용하여 value값 구분. 만약 어떤 유저가 2,3,4번 코스를 조회했다면 “2-3-4-” 형태로 value값 저장
- 키(유저ID)를 통해 Value(CourseId)들을 조회해온 뒤 호출된 courseId와 중복검사를 진행하여 조회한적이 없으면 조회수 증가시켜주는 로직!
- TTL(데이터 자동 삭제)은 자정으로 설정

---  
