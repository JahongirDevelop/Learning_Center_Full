package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.LessonStatus;


import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LessonEntity extends BaseEntity {
    private UUID groupId;
    private Integer lessonNumber;
    private Integer module;
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus;
}
