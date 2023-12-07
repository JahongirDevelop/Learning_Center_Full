package uz.pdp.learning_center_full.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonAttendanceResponse {
    private LessonResponse lessonResponse;
    private List<AttendanceResponse> attendanceResponseList;
}
