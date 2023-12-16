package uz.pdp.learning_center_full.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.ApplicationCr;
import uz.pdp.learning_center_full.dto.response.ApplicationResponse;
import uz.pdp.learning_center_full.entity.enums.ApplicationStatus;
import uz.pdp.learning_center_full.service.ApplicationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    @PostMapping("/create")
    public ResponseEntity<ApplicationResponse> create(@RequestBody ApplicationCr applicationCR){
        return ResponseEntity.ok(applicationService.create(applicationCR));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/get_application{id}")
    public ResponseEntity<ApplicationResponse> getApplication(@PathVariable UUID id){
        return applicationService.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getAll")
    public List<ApplicationResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return applicationService.getAll(page, size);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/getAllByStatus")
    public List<ApplicationResponse> getAllBYStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam ApplicationStatus status){
        return applicationService.getAllByStatus(page, size,status);
    }
}

