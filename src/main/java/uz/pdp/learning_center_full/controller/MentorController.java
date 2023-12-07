package uz.pdp.learning_center_full.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.learning_center_full.dto.request.MentorCr;
import uz.pdp.learning_center_full.dto.response.MentorResponse;
import uz.pdp.learning_center_full.service.MentorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mentors")
public class MentorController {
    private final MentorService mentorService;

    @PostMapping("/create")
    public void addMentor(@RequestBody MentorCr mentorCr) {
//        return mentorService.addMentor(mentorCr);
    }
}
