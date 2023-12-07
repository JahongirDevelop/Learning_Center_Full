package uz.pdp.learning_center_full.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.CourseCr;
import uz.pdp.learning_center_full.dto.response.CourseResponse;
import uz.pdp.learning_center_full.service.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CourseResponse> create(@RequestBody @Valid CourseCr courseCr) {
        return ResponseEntity.ok(courseService.create(courseCr));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_by_id/{course_id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable UUID course_id) {
        return courseService.findById(course_id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_all")
    public ResponseEntity<List<CourseResponse>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getAll(page,size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_by_id/{course_id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID course_id){
        return courseService.deleteById(course_id);
    }

}
