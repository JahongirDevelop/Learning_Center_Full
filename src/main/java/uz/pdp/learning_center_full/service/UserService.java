package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.config.jwt.JwtService;
import uz.pdp.learning_center_full.dto.request.AuthDto;
import uz.pdp.learning_center_full.dto.request.UserCr;
import uz.pdp.learning_center_full.dto.request.UserUpdate;
import uz.pdp.learning_center_full.dto.response.JwtResponse;
import uz.pdp.learning_center_full.dto.response.MentorResponse;
import uz.pdp.learning_center_full.dto.response.StudentResponse;
import uz.pdp.learning_center_full.dto.response.UserResponse;
import uz.pdp.learning_center_full.entity.MentorInfo;
import uz.pdp.learning_center_full.entity.StudentInfo;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.repository.MentorRepository;
import uz.pdp.learning_center_full.repository.StudentRepository;
import uz.pdp.learning_center_full.repository.UserRepository;

import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.learning_center_full.entity.enums.UserRole.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository ;
    private final ModelMapper modelMapper;
    private final MailSenderService senderService;
    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;
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
        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        if (dto.getPassword().equals(user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new AuthenticationCredentialsNotFoundException("password didn't match");
    }

    public  <T> T me(Principal principal) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(principal.getName()))
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        UserRole role = userEntity.getRole();
        if (role == STUDENT){
            StudentResponse studentResponse = modelMapper.map(userEntity,StudentResponse.class);
            StudentInfo studentInfo = studentRepository.findStudentInfoByUserEntityId(userEntity.getId())
                    .orElseThrow(() -> new DataNotFoundException("Not found!"));
            studentResponse.setId(studentInfo.getId());
            studentResponse.setGroupId(studentInfo.getGroupId());
            studentResponse.setRating(studentInfo.getRating());
            return (T) studentResponse;
        } else if (role == MENTOR) {
            MentorResponse mentorResponse =  modelMapper.map(userEntity,MentorResponse.class);
            MentorInfo mentorInfo = mentorRepository.findMentorInfoByUserEntityId(userEntity.getId())
                .orElseThrow(() -> new DataNotFoundException("Not found!"));
            mentorResponse.setSalary(mentorInfo.getSalary());
            mentorResponse.setExperience(mentorInfo.getExperience());
            mentorResponse.setSubject(mentorInfo.getSubject());
            return (T)mentorResponse;
        }
        return (T) modelMapper.map(userEntity,UserResponse.class);
    }
    public Object update(UserUpdate userUpdate,Principal principal) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(principal.getName()))
                .orElseThrow(() -> new DataNotFoundException("User not found!"));

        if(!Objects.equals(userUpdate.getName(),null)){
            userEntity.setName(userUpdate.getName());
        }if (!Objects.equals(userUpdate.getSurname(),null)) {
            userEntity.setSurname(userUpdate.getSurname());
        }if (!Objects.equals(userUpdate.getPhoneNumber(),null)) {
            userEntity.setPhoneNumber(userUpdate.getPhoneNumber());
        }if (!Objects.equals(userUpdate.getEmail(),null)) {
            userEntity.setEmail(userUpdate.getEmail());
        }if (!Objects.equals(userUpdate.getPassword(),null)) {
            userEntity.setPassword(userUpdate.getPassword());
        }
        UserEntity userEntity1 = userRepository.save(userEntity);
        return modelMapper.map(userEntity1,UserResponse.class);

    }
}
