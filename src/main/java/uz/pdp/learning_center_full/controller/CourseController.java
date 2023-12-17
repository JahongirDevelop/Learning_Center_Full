package uz.pdp.learning_center_full.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.CourseCr;
import uz.pdp.learning_center_full.dto.response.CourseResponse;
import uz.pdp.learning_center_full.service.CourseService;

import java.util.List;
import java.util.UUID;
//dsd

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

//dsa
    @PermitAll
    @GetMapping("/get_by_id/{course_id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable UUID course_id) {
        return courseService.findById(course_id);
    }
    @PermitAll
    @GetMapping("/get_all")
    public ResponseEntity<List<CourseResponse>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getAll(page,size);
    }



}
