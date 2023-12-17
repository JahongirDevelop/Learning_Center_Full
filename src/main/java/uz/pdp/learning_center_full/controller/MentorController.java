package uz.pdp.learning_center_full.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uz.pdp.learning_center_full.dto.request.AttendanceCr;
import uz.pdp.learning_center_full.dto.response.*;
import uz.pdp.learning_center_full.service.AttendanceService;
import uz.pdp.learning_center_full.service.GroupService;
import uz.pdp.learning_center_full.service.LessonService;

import uz.pdp.learning_center_full.service.MentorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mentors")
public class MentorController {


    private final LessonService lessonService;
    private final AttendanceService attendanceService;
    private final GroupService groupService;

    @PreAuthorize(" hasRole('MENTOR') ")
    @GetMapping("/mentor-groups/{mentor_id}")
    public List<GroupResponse> getByMentorID(@PathVariable UUID mentor_id){
        return groupService.getByMentorId(mentor_id);
    }

    private final MentorService mentorService;



    @PreAuthorize(" hasRole('MENTOR') ")

    @GetMapping("getAllAttendancesWithLessonInLastModule{group_id}")
    public ResponseEntity<List<LessonAttendanceResponse>> getAllAttendancesWithLessonInLastModule(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable UUID group_id){
        return ResponseEntity.ok(attendanceService.getAllAttendancesWithLesson(group_id,page,size));
    }


    @PreAuthorize(" hasRole('MENTOR') ")
    @GetMapping("getAttendanceWithLessonByModule")
    public ResponseEntity<List<LessonAttendanceResponse>> getAttendanceWithLessonByModule(@RequestParam UUID group_id,@RequestParam Integer module){
        System.out.println("module = " + module);
        return ResponseEntity.ok(attendanceService.getAttendanceWithLessonByModule(group_id,module));
    }
    @PreAuthorize(" hasRole('MENTOR') ")
    @GetMapping("/get_All")
    public ResponseEntity<List<LessonResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.status(200).body(lessonService.getAll(page, size));
    }
    @PreAuthorize(" hasRole('MENTOR') ")
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.status(200).body(lessonService.findById(id));
    }
    @PreAuthorize(" hasRole('MENTOR') ")
    @GetMapping("/get-lessons/{groupId}")
    public ResponseEntity<List<LessonResponse>> getAll(@PathVariable UUID groupId) {
        return ResponseEntity.status(200).body(lessonService.getLesson(groupId));
    }
    @PreAuthorize("hasRole('MENTOR') ")
    @PostMapping("/start_lesson{lessonId}/{groupId}")
    public ResponseEntity<LessonResponse>  startLesson(
            @PathVariable UUID lessonId, @PathVariable UUID groupId){
        return lessonService.startLesson(lessonId, groupId);
    }

    @PreAuthorize("hasRole('MENTOR') ")
    @PostMapping("/finish_lesson")
    public ResponseEntity<String> finishLesson(@RequestBody List<AttendanceCr> attendanceCrList){
        return lessonService.finishLesson(attendanceCrList);
    }




}
