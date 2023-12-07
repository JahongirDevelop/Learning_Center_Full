package uz.pdp.learning_center_full.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.learning_center_full.dto.response.LessonAttendanceResponse;
import uz.pdp.learning_center_full.service.AttendanceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {
    private final AttendanceService attendanceService;

//    @PostMapping("/create")
//    public ResponseEntity<AttendanceResponse> create(@Valid @RequestBody AttendanceCr attendanceCr){
//        return ResponseEntity.status(200).body(attendanceService.create(attendanceCr));
//    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    @GetMapping("/get_student_attendances/{student_id}")
    public ResponseEntity<List<LessonAttendanceResponse>> getStudentAttendances(
            @PathVariable UUID student_id) {
        return ResponseEntity.ok(attendanceService.getStudentAttendances(student_id));
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    @GetMapping("getAllAttendancesWithLesson{group_id}")
    public ResponseEntity<List<LessonAttendanceResponse>> getAllAttendancesWithLesson(
            @PathVariable UUID group_id){
        return ResponseEntity.ok(attendanceService.getAllAttendancesWithLesson(group_id));
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    @GetMapping("getAttendanceWithLessonByModule{group_id}")
    private ResponseEntity<List<LessonAttendanceResponse>> getAttendanceWithLessonByModule(@PathVariable UUID group_id){
        return ResponseEntity.ok(attendanceService.getAttendanceWithLessonByModule(group_id));
    }

}
