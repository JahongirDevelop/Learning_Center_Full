package uz.pdp.learning_center_full.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.StudentCR;
import uz.pdp.learning_center_full.dto.response.StudentResponse;
import uz.pdp.learning_center_full.dto.response.StudentUpdateDTO;
import uz.pdp.learning_center_full.service.StudentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCR studentCR) {
        return ResponseEntity.status(200).body(studentService.create(studentCR));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<StudentResponse> update(
            @RequestParam UUID studentId,
            @RequestBody StudentUpdateDTO update) {
        return ResponseEntity.status(200).body(studentService.updateById(studentId, update));
    }

    @DeleteMapping("delete/{studentId}")
    public ResponseEntity<String> deleteStudentById(@PathVariable UUID studentId) {
        return ResponseEntity.status(200).body(studentService.deleteById(studentId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<StudentResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(200).body(studentService.getAll(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-group-students/{group_id}")
    public ResponseEntity<List<StudentResponse>> getGroupStudents(@PathVariable UUID group_id){
        return ResponseEntity.status(200).body(studentService.getByGroupId(group_id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create_by_application")
    public ResponseEntity<StudentResponse> createByApplication(@RequestParam UUID application_id, UUID group_id){
        return studentService.createByApplication(application_id,group_id);
    }
    @PreAuthorize("hasRole('MENTOR') or hasRole('ADMIN') or hasRole('STUDENT')")
    @GetMapping("/get_students_by_rating")
    public ResponseEntity<List<StudentResponse>> getStudentByRating(UUID groupId){
        return ResponseEntity.status(200).body(studentService.getStudentByRating(groupId));
    }


}
