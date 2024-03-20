package com.ssafy.fullerting.favorite.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class FavoriteResponseSerializer extends JsonSerializer<FavoriteResponse> {

    @Override
    public void serialize(FavoriteResponse favoriteResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeBooleanField("islike", favoriteResponse.isIslike());
        jsonGenerator.writeNumberField("isLikeCnt", favoriteResponse.getIsLikeCnt());
        jsonGenerator.writeEndObject();
    }

}
