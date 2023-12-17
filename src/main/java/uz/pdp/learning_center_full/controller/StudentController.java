package uz.pdp.learning_center_full.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.StudentCR;
import uz.pdp.learning_center_full.dto.response.*;
import uz.pdp.learning_center_full.entity.enums.Subject;
import uz.pdp.learning_center_full.service.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/get_students_by_rating")
    public ResponseEntity<List<StudentResponse>> getStudentByRating(Principal principal){
        return ResponseEntity.status(200).body(studentService.getStudentByRating(principal));
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my-attendance")
    public  ResponseEntity<List<LessonAttendanceResponse>> studentAttendances(Principal principal){
        return ResponseEntity.ok(attendanceService.getStudentAttendances(UUID.fromString(principal.getName())));
    }



}
