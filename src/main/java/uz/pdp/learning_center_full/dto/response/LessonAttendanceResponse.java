package uz.pdp.learning_center_full.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessonAttendanceResponse {
    private LessonResponse lessonResponse;
    private List<AttendanceResponse> attendanceResponseList;
}
