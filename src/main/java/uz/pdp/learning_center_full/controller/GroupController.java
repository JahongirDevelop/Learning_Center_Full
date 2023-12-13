package uz.pdp.learning_center_full.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.GroupCr;
import uz.pdp.learning_center_full.dto.request.UpdateGroupDto;
import uz.pdp.learning_center_full.dto.response.GroupResponse;
import uz.pdp.learning_center_full.entity.GroupEntity;
import uz.pdp.learning_center_full.service.GroupService;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody GroupCr groupCr) {
        return ResponseEntity.status(200).body(groupService.create(groupCr));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public GroupResponse getGroup(@PathVariable UUID id){
        return groupService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN') ")
    @GetMapping("/get-all")
    public List<GroupEntity> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return groupService.getAll(page, size);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    @GetMapping("/mentor-groups/{mentor_id}")
    public List<GroupResponse> getByMentorID(@PathVariable UUID mentor_id){
        return groupService.getByMentorId(mentor_id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-by-course-id/{course_id}")
    public List<GroupResponse> getByCourseId(@PathVariable UUID course_id){
        return groupService.getByCourseId(course_id);
     }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-available-groups-by-course-id/{course_id}")
    public List<GroupResponse> getAvailableGroupsByCourseId(@PathVariable UUID course_id){
        return groupService.getAvailableGroupsByCourseId(course_id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update{groupId}")
    public ResponseEntity<GroupResponse> update(@PathVariable @NotNull UUID groupId,
                                                @Valid @RequestBody UpdateGroupDto updateGroupDto){

        return ResponseEntity.status(200).body(groupService.update(groupId, updateGroupDto));

    }


}
