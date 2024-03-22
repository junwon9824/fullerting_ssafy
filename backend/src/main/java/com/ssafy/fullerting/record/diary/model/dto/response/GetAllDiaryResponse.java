package com.ssafy.fullerting.record.diary.model.dto.response;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GetAllDiaryResponse {
    private String diarySelectedAt;
    private List<GetSelectedAtDiaryResponse> GetSelectedAtDiaryResponse;
}
