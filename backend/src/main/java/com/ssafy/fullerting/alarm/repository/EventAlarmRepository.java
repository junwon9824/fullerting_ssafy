package com.ssafy.fullerting.alarm.repository;

import com.ssafy.fullerting.alarm.model.entity.EventAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAlarmRepository extends JpaRepository<EventAlarm,Long> {
}
