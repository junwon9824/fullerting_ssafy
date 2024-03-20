package com.ssafy.fullerting.global.s3.servcie;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.fullerting.global.s3.model.entity.response.S3ManyFilesResponse;
import com.ssafy.fullerting.global.s3.model.entity.response.S3OneFileResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AmazonS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    // 썸네일 하나만 받는 경우
    public S3OneFileResponse uploadThunmail(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "업로드할 파일이 없습니다.");
        }

        return new S3OneFileResponse(uploadToS3ReturnURL(multipartFile, 0));
    }
    
    
    // 여러개의 파일을 받는 경우
    public S3ManyFilesResponse uploadFiles(List<MultipartFile> multipartFiles){
        S3ManyFilesResponse response = new S3ManyFilesResponse();

        // 파일을 순차적으로 처리하고, 각 파일에 대한 URL을 수집
        IntStream.range(0, multipartFiles.size())
                .forEach(i -> {
                    MultipartFile file = multipartFiles.get(i);
                    String url = uploadToS3ReturnURL(file, i); // 파일을 S3에 업로드하고, 생성된 URL을 반환
                    response.addUrl("url" + (i + 1), url); // 맵에 URL 추가
                });

        return response;
    }

    // 인덱스 번호로 구분하여 S3에 업로드
    // 업로드 된 객체에 접근할 수 있는 url 반환
    private String uploadToS3ReturnURL(MultipartFile file, int index) {
        String UUIDFileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(bucket, UUIDFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            log.info("파일 업로드 : [{}] to [{}] ", file.getOriginalFilename(), UUIDFileName);
            return getS3FileURL(UUIDFileName);
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
    }


    
    // S3 저장된 파일명을 통해 S3 객체 url 조회
    public String getS3FileURL(String hashedFileName) {
        return amazonS3.getUrl(bucket, hashedFileName).toString();
    }


    // 파일명을 난수화하기 위해 UUID 를 활용하여 난수를 돌린다.
    public String createFileName(String originalFileName){
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }


    // 확장자 추출
    private String getFileExtension(String fileName){
        // 허용하는 파일 확장자 목록
        List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".svg");

        try{
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (!allowedExtensions.contains(extension)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "jpg, jpeg, svg, png 파일만 등록 가능합니다.");
            }

            return extension;
        } catch (StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + fileName + ") 입니다.");
        }
    }


    public void deleteFile(String fileName){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        System.out.println(bucket);
    }
}
