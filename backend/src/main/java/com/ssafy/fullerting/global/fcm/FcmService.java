//package com.ssafy.fullerting.global.fcm;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class FcmService {
//    // 비밀키 경로 환경 변수 ( 필수 )
//    @Value("${fcm.service-account-file}")
//    private String serviceAccountFilePath;
//
//    // topic 이름 환경 변수
//    @Value("${fcm.topic-name}")
//    private String topicName;
//
//    // 프로젝트 아이디 환경 변수 ( Firbase 프로젝트 여러개 쓰는 경우 SDK 초기화 때 명시필요 )
//    @Value("${fcm.project-id}")
//    private String projectId;
//
//    // 의존성 주입이 이루어진 후 초기화를 수행한다.
//    @PostConstruct
//    public void initialize() throws IOException {
//        try {
//            ClassPathResource resource = new ClassPathResource(serviceAccountFilePath);
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
//                    .setProjectId(projectId)
//                    .build();
//            //입력한 정보를 이용하여 initialze 해준다.
//            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
//            log.info("FCM Admin SDK Load Success : {}", firebaseApp.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // 해당 지정된 topic에 fcm를 보내는 메서드
//    public void sendMessageByTopic(String title, String body) throws IOException, FirebaseMessagingException {
//        FirebaseMessaging.getInstance().send(Message.builder()
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(body)
//                        .build())
//                .setTopic(topicName)
//                .build());
//
//    }
//
//    // 받은 token을 이용하여 fcm를 보내는 메서드
//    public void sendMessageByToken(String title, String body,String token) throws FirebaseMessagingException{
//        FirebaseMessaging.getInstance().send(Message.builder()
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(body)
//                        .build())
//                .setToken(token)
//                .build());
//    }
//
//
//
//}
