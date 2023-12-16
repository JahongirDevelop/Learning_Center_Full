package uz.pdp.learning_center_full.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.AttendanceCr;
import uz.pdp.learning_center_full.dto.response.LessonResponse;
import uz.pdp.learning_center_full.service.LessonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
public class LessonController {
    private final LessonService lessonService;

//    @PostMapping("/create")
//    public ResponseEntity<LessonResponse> create(@RequestBody LessonCR lessonCR){
//        return  ResponseEntity.status(200).body(lessonService.create(lessonCR));
//    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @GetMapping("/get_All")
    public ResponseEntity<List<LessonResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.status(200).body(lessonService.getAll(page, size));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.status(200).body(lessonService.findById(id));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @GetMapping("/get-lessons/{groupId}")
    public ResponseEntity<List<LessonResponse>> getAll(@PathVariable UUID groupId) {
        return ResponseEntity.status(200).body(lessonService.getLesson(groupId));
    }
    @PreAuthorize("hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @PostMapping("/start_lesson{lessonId}/{groupId}")
    public ResponseEntity<LessonResponse>  startLesson(
            @PathVariable UUID lessonId, @PathVariable UUID groupId){
        return lessonService.startLesson(lessonId, groupId);
    }
    @PreAuthorize("hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @PostMapping("/finish_lesson")
    public ResponseEntity<String> finishLesson(@RequestBody List<AttendanceCr> attendanceCrList){
         return lessonService.finishLesson(attendanceCrList);
    }

}
