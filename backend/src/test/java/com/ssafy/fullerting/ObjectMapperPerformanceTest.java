package com.ssafy.fullerting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ObjectMapperPerformanceTest {

    // 1. 빈으로 등록된 ObjectMapper (Autowired로 주입)
    @Autowired
    private ObjectMapper objectMapper;

    private static List<MyObject> objects;

    @BeforeAll
    public static void setup() {
        // 테스트를 위한 객체 리스트 준비
        objects = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            objects.add(new MyObject(i, "Name " + i));
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testWithReusedObjectMapper() throws Exception {
        long startTime = System.nanoTime();
        for (MyObject obj : objects) {
            String json = objectMapper.writeValueAsString(obj); // 직렬화
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Reused ObjectMapper time: " + duration + " ms");

        // JUnit 5 Timeout을 통해 실행시간 제한
        assertTrue(duration < 500, "Execution time should be less than 500ms");
    }

    @Test
//    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testWithNewObjectMapper() throws Exception {
        long startTime = System.nanoTime();
        for (MyObject obj : objects) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String json = mapper.writeValueAsString(obj); // 직렬화
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("New ObjectMapper time: " + duration + " ms");

        // JUnit 5 Timeout을 통해 실행시간 제한
        assertTrue(duration < 500, "Execution time should be less than 500ms");
    }
}

class MyObject {
    private int id;
    private String name;

    public MyObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
