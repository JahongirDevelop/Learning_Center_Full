package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.config.jwt.JwtService;
import uz.pdp.learning_center_full.dto.request.AuthDto;
import uz.pdp.learning_center_full.dto.request.UserCr;
import uz.pdp.learning_center_full.dto.response.JwtResponse;
import uz.pdp.learning_center_full.dto.response.MentorResponse;
import uz.pdp.learning_center_full.dto.response.UserResponse;
import uz.pdp.learning_center_full.entity.MentorInfo;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.repository.UserRepository;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository ;
    private final ModelMapper modelMapper;
    private final MailSenderService senderService;
    private final JwtService jwtService;
    public UserResponse addAdmin(UserCr userCr){
        String verificationCode = senderService.sendVerificationCode(userCr.getEmail());
        UserEntity save = userRepository.save(modelMapper.map(userCr, UserEntity.class));
        save.setEmail(userCr.getEmail());
        save.setPassword(verificationCode);
        save.setRole(UserRole.ADMIN);
        userRepository.save(save);
        return modelMapper.map( save,UserResponse.class);
    }

    public JwtResponse signIn(AuthDto dto) {
        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new DataNotFoundException("user not found"));
        if (dto.getPassword().equals(user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new AuthenticationCredentialsNotFoundException("password didn't match");
    }

    public ResponseEntity<UserResponse> me(Principal principal) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(principal.getName())).get();
        return ResponseEntity.ok(modelMapper.map(userEntity, UserResponse.class));

    }
}
