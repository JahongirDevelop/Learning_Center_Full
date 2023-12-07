package uz.pdp.learning_center_full.dto.response;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    private UUID lessonId;
    private UUID studentId;
    private UUID groupId;
    private boolean isExist;
    private String description; // sabab
    private UUID id;
}
