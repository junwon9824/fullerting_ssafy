package com.ssafy.fullerting.image.service;

import com.ssafy.fullerting.image.exception.ImageErrorCode;
import com.ssafy.fullerting.image.exception.ImageException;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    void imageregist(Image image) {

        imageRepository.save(image);
    }

    Image getimagebyid(Long imageid) {
        return imageRepository.findById(imageid).orElseThrow(() -> new ImageException(ImageErrorCode.NOT_EXISTS));

    }


}

