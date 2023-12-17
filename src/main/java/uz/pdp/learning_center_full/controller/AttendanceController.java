package uz.pdp.learning_center_full.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.response.LessonAttendanceResponse;
import uz.pdp.learning_center_full.dto.response.MentorResponse;
import uz.pdp.learning_center_full.dto.response.UserResponse;
import uz.pdp.learning_center_full.service.AttendanceService;

import java.security.Principal;
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
//
//    @GetMapping("/get_student_attendances/{student_id}")
//    public ResponseEntity<List<LessonAttendanceResponse>> getStudentAttendances(
//            @PathVariable UUID student_id) {
//        return ResponseEntity.ok(attendanceService.getStudentAttendances(student_id));
//    }
    @PreAuthorize("hasRole('STUDENT') or hasRole('SUPER_ADMIN')")
    @GetMapping("/my-attendance")
    public  ResponseEntity<List<LessonAttendanceResponse>> studentAttendances(Principal principal){
        return ResponseEntity.ok(attendanceService.getStudentAttendances(UUID.fromString(principal.getName())));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('SUPER_ADMIN')")

    @GetMapping("getAllAttendancesWithLessonInLastModule{group_id}")
    public ResponseEntity<List<LessonAttendanceResponse>> getAllAttendancesWithLessonInLastModule(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable UUID group_id){
        return ResponseEntity.ok(attendanceService.getAllAttendancesWithLesson(group_id,page,size));
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
//    @GetMapping("/get_all")
//    public ResponseEntity<List<MentorResponse>> getAll(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size){
//        return mentorService.getAll(page,size);
//    }

///sdds

    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @GetMapping("getAttendanceWithLessonByModule")
    public ResponseEntity<List<LessonAttendanceResponse>> getAttendanceWithLessonByModule(@RequestParam UUID group_id,@RequestParam Integer module){
        System.out.println("module = " + module);
        return ResponseEntity.ok(attendanceService.getAttendanceWithLessonByModule(group_id,module));
    }

}
