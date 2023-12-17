package uz.pdp.learning_center_full.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.MentorUpdate;
import uz.pdp.learning_center_full.dto.response.MentorResponse;
import uz.pdp.learning_center_full.service.MentorService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mentors")
public class MentorController {
    private final MentorService mentorService;
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/create")
//    public ResponseEntity<MentorResponse> addMentor(@RequestBody MentorCr mentorCr) {
//        return mentorService.addMentor(mentorCr);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_by_id/{mentor_id}")
    public ResponseEntity<MentorResponse> getByID(@PathVariable UUID mentor_id){
        return mentorService.getById(mentor_id);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/get_all")
    public ResponseEntity<List<MentorResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return mentorService.getAll(page,size);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete_by_id/{mentor_id}")
    public ResponseEntity<String> deleteByID(@PathVariable UUID mentor_id){
        return mentorService.deleteByID(mentor_id);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<MentorResponse> updateProfile(@RequestParam UUID mentorId, @RequestBody MentorUpdate mentorUp){
        return mentorService.update(mentorId,mentorUp);
    }
    @PreAuthorize("hasRole('MENTOR') or hasRole('SUPER_ADMIN')")
    @GetMapping("/me")
    public  ResponseEntity<MentorResponse> myProfile(Principal principal){
        return mentorService.me(principal);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/get-mentors-by-course/{course_id}")
    public ResponseEntity<List<MentorResponse>> getByCourseId( @PathVariable UUID course_id){
       return mentorService.getMentorByCourseId(course_id);

    }


}
