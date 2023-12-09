package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceEntity extends BaseEntity {



    private UUID lessonId;
    private UUID studentId;
    private UUID groupId;
    private boolean isExist;
    private String description; // sabab
    private int points;
}
