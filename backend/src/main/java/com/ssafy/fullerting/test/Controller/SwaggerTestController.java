//package com.ssafy.fullerting.test.Controller;
//
//import com.ssafy.fullerting.test.TestDto;
//import com.ssafy.fullerting.test.TestRto;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//
//@Tag(name = "[TEST] 테스트 API" , description = "스웨거 테스트용 유저 회원가입")
//@RestController
//@RequestMapping("/v1/test")
//@RequiredArgsConstructor
//public class SwaggerTestController {
//
//    @Operation(summary = "테스트용 메서드", description = "입력받은 값을 그대로 응답한다")
//    @ApiResponse(responseCode = "200", description = "테스트 성공")
//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/hello")
//    public TestDto printHello(@RequestBody TestRto testRTO) {
//
//        return TestDto.builder()
//                .code(testRTO.getCode())
//                .pwd(testRTO.getPwd())
//                .build();
//    }
//}
