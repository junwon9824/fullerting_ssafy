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
    @JoinColumn(name = "receive_user_id")
    private CustomUser receiveUserId;

    @ManyToOne
    @JoinColumn(name = "send_user_id")
    private CustomUser sendUserId;

    @Column(name = "event_alarm_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EventAlarmType type;

    @Column(name = "event_alarm_content", nullable = false, length = 200)
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "event_alarm_is_checked")
    private boolean isChecked = false;

    @Column(name = "event_alarm_redirect", nullable = false, length = 255)
    private String redirect;


    // JPA 프록시 객체 생성을 위해 기본 생성자는 필수
    protected EventAlarm() {}

    // 빌더 생성자 -> 빌더 객체로부터 값을 받아 EventAlarm 초기화 
    public EventAlarm(Builder builder) {
        this.receiveUserId = builder.receiveUserId;
        this.sendUserId = builder.sendUserId;
        this.type = builder.type;
        this.content = builder.content;
        this.isChecked = builder.isChecked;
        this.redirect = builder.redirect;
    }
    
    
    // EventAlarm 클래스이 정적 팩토리 메서드
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CustomUser receiveUserId;
        private CustomUser sendUserId;
        private EventAlarmType type;
        private String content;
        private boolean isChecked = false;
        private String redirect;


        public Builder receiveUserId(CustomUser receiveUserId) {
            this.receiveUserId = receiveUserId;
            return this;
        }

        public Builder sendUserId(CustomUser sendUserId) {
            this.sendUserId = sendUserId;
            return this;
        }

        public Builder type(EventAlarmType type) {
            this.type = type;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder isChecked(boolean isChecked) {
            this.isChecked = isChecked;
            return this;
        }

        public Builder redirect(String redirect) {
            this.redirect = redirect;
            return this;
        }

        public EventAlarm build() {
            return new EventAlarm(this);
        }
    }
}
