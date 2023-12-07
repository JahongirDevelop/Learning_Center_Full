package uz.pdp.learning_center_full.dto.request;

import lombok.*;
import uz.pdp.learning_center_full.entity.enums.LessonStatus;


import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessonCR {
    private UUID groupId;
    private Integer lessonNumber;
    private LessonStatus lessonStatus;

}
