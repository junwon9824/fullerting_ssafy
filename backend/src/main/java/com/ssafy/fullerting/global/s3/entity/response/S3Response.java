package com.ssafy.fullerting.global.s3.entity.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class S3Response {
    private Map<String, String> urls;

    public S3Response() {
        urls = new HashMap<>();
    }

    public void addUrl(String key, String url) {
        this.urls.put(key,url);
    }
}
