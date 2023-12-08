package uz.pdp.learning_center_full.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.learning_center_full.dto.request.AuthDto;
import uz.pdp.learning_center_full.dto.request.UserCr;
import uz.pdp.learning_center_full.dto.response.JwtResponse;
import uz.pdp.learning_center_full.dto.response.UserResponse;
import uz.pdp.learning_center_full.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService ;

    @PostMapping("/create-admin")
    public ResponseEntity<UserResponse> createAdmin(@Valid @RequestBody UserCr studentCR) {
        return ResponseEntity.status(200).body(userService.addAdmin(studentCR));
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse test(@Valid @RequestBody AuthDto dto) {
        return userService.signIn(dto);
    }

}
