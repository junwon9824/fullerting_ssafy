package com.ssafy.fullerting.alarm.service;

import com.ssafy.fullerting.alarm.model.EventAlarmType;
import com.ssafy.fullerting.alarm.model.dto.response.MyEventAlarmResponse;
import com.ssafy.fullerting.alarm.model.entity.EventAlarm;
import com.ssafy.fullerting.alarm.repository.EventAlarmRepository;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventAlarmService {
    private final EventAlarmNotificationService eventAlarmNotificationService;
    private final EventAlarmRepository eventAlarmRepository;
    private final UserService userService;
    // 알림 트리거
    // 1. 내가 쓴 게시물에 댓글이 달렸을 때
    // 2. 채팅이 왔을 때 (채팅이 생성됐을 때)
    // 3. 가격제안 왔을 때
    // 4. 내가 입찰한 게시물의 거래가 종료되었을 때
    // 5. 뱃지 획득했을 때
    // 6. 등급 올랐을 때

    public List<MyEventAlarmResponse> getEventAlarmsForUser() {
        // 첫 페이지, 페이지 당 30개 항목으로 페이징 설정
        Slice<EventAlarm> eventAlarmSlice = eventAlarmRepository.
                findByReceiveUserId(
                        userService.getUserInfo().getId(),
                        PageRequest.of(0, 10)
                );
        log.info("알람 요청자 : {} ", userService.getUserInfo().getId());
        log.info("내 알람리스트 : {}", eventAlarmSlice.toString());
        // 조회된 이벤트 알람 리스트 반환

        return eventAlarmSlice.getContent().stream().map(eventAlarm ->
                MyEventAlarmResponse.builder()
                        .alarmId(eventAlarm.getAlarmId())
                        .alarmType(eventAlarm.getType().toString())
                        .alarmContent(eventAlarm.getContent())
                        .alarmRedirect(eventAlarm.getRedirect())
                        .isChecked(eventAlarm.isChecked())
                        .build()
        ).collect(Collectors.toList());
    }

    // 3. 가격제안 왔을 때
    // 현재 사용자의 알림함에 저장하는 메서드
    // 실행조건 : 입찰자가 입찰하기를 눌렀을 때
    public void notifyAuctionBidReceived(CustomUser bidUser, ExArticle exArticle, String redirectURL) {
        // 내가 가격제안 게시물을 올렸는데
        // 누군가가 입찰을 했을 때 알림

        EventAlarm alarm = EventAlarm.builder()
                .receiveUser(exArticle.getUser())
                .sendUser(bidUser)
                .type(EventAlarmType.작물거래)
                .content(bidUser.getNickname() + "님이 " + "#"+exArticle.getTitle()+"#" +"에 가격을 제안하셨어요.")
                .redirect(redirectURL)
                .build();

        eventAlarmRepository.save(alarm);
        eventAlarmNotificationService.sendAlarmToReceiveUser(alarm);
        log.info("이벤트 알람 도착 : {} ", alarm);
    }

}
