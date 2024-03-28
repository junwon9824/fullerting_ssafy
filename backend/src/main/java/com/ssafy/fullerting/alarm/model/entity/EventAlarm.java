package com.ssafy.fullerting.alarm.model.entity;

import com.ssafy.fullerting.alarm.model.EventAlarmType;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class EventAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser customUser;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser fromUserId;

    @Column(name = "event_alarm_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EventAlarmType type;

    @Column(name = "event_alarm_content", nullable = false, length = 200)
    private String content;

    @CreationTimestamp()
    private LocalDateTime createdAt;

    @Column(name = "event_alarm_is_checked")
    private boolean isChecked;

    @Column(name = "event_alram_redirect", nullable = false, length = 255)
    private String redirect;
}
