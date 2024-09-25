//package com.ssafy.fullerting.global.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class KafkaListeners {
//    @KafkaListener(topics = "${setting.topics}",groupId = "${spring.kafka.consumer.group-id}")
//    public void consume(ConsumerRecord<String, String> consumerRecord, @Header(KafkaHeaders.OFFSET) Long offset
//            , Acknowledgment acknowledgment
//            , Consumer<?, ?> consumer){
//        try {
//
//            log.info("Consumer Data = {}, Offset = {}, Header OffSet = {}, Partition = {}"
//                    , consumerRecord.value(), consumerRecord.offset(),offset,consumerRecord.partition());
//            //처리 후 커밋
//            //해당 비지니스 로직 처리 후 커밋로직 작성
//            consumer.commitAsync();
//        }
//        catch (Exception e){
//            log.error(e.getMessage());
//        }
//
//    }
//}