package uz.pdp.learning_center_full.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.learning_center_full.dto.request.ApplicationCr;
import uz.pdp.learning_center_full.dto.response.ApplicationResponse;
import uz.pdp.learning_center_full.service.ApplicationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
//    @PostMapping("/create")
//    public ResponseEntity<ApplicationResponse> create(@RequestBody ApplicationCr applicationCR){
//        return ResponseEntity.ok(applicationService.create(applicationCR));
//    }
}
