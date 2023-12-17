package uz.pdp.learning_center_full.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_center_full.dto.request.AuthDto;
import uz.pdp.learning_center_full.dto.request.UserCr;
import uz.pdp.learning_center_full.dto.response.JwtResponse;
import uz.pdp.learning_center_full.dto.response.StudentProfile;
import uz.pdp.learning_center_full.dto.response.UserResponse;
import uz.pdp.learning_center_full.service.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService ;
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<UserResponse> createAdmin(@Valid @RequestBody UserCr studentCR) {
        return ResponseEntity.status(200).body(userService.addAdmin(studentCR));
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@Valid @RequestBody AuthDto dto) {
        return userService.signIn(dto);
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PermitAll
    @GetMapping("/me")
    public  ResponseEntity<Object> myProfile(Principal principal){
        return ResponseEntity.ok(userService.me(principal));
    }
}
