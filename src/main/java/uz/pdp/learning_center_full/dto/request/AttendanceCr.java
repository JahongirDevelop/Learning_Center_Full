package uz.pdp.learning_center_full.dto.request;

import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceCr {
    private UUID lessonId;
    private UUID studentId;
    private UUID groupId;
    private boolean isExist;
    private String description;
    private int points;
}
