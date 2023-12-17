package uz.pdp.learning_center_full.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.MentorCr;
import uz.pdp.learning_center_full.dto.request.MentorUpdate;
import uz.pdp.learning_center_full.dto.response.MentorResponse;
import uz.pdp.learning_center_full.entity.CourseEntity;
import uz.pdp.learning_center_full.entity.MentorInfo;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.repository.CourseRepository;
import uz.pdp.learning_center_full.repository.MentorRepository;
import uz.pdp.learning_center_full.repository.UserRepository;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final ModelMapper modelMapper;
    private final MentorRepository mentorRepository;
    private final MailSenderService senderService;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    public ResponseEntity<MentorResponse> addMentor(MentorCr mentorCr) {
        String verificationCode = senderService.sendVerificationCode(mentorCr.getEmail());;
            UserEntity userEntity = UserEntity.builder()
                    .name(mentorCr.getName())
                    .surname(mentorCr.getSurname())
                    .email(mentorCr.getEmail())
                   .password(verificationCode)
                    .phoneNumber(mentorCr.getPhoneNumber())
                    .role(UserRole.MENTOR)
                    .build();
        UserEntity save = userRepository.save(userEntity);
        MentorResponse mentorResponse = modelMapper.map(save,MentorResponse.class);
        MentorInfo mentorInfo = MentorInfo.builder()
                    .salary(mentorCr.getSalary())
                    .subject(mentorCr.getSubject())
                    .experience(mentorCr.getExperience())
                    .userEntity(userEntity)
                    .build();
        MentorInfo save1 = mentorRepository.save(mentorInfo);
        mentorResponse.setSalary(mentorInfo.getSalary());
        mentorResponse.setSubject(mentorInfo.getSubject());
        mentorResponse.setExperience(mentorInfo.getExperience());
        mentorResponse.setId(save1.getId());
        return  ResponseEntity.ok(mentorResponse);
    }


    public ResponseEntity<MentorResponse> getById(UUID mentorID) {
        MentorInfo mentorEntity = mentorRepository.findById(mentorID)
                .orElseThrow(() -> new DataNotFoundException("Invalid value"));
        UserEntity userEntity = userRepository.findById(mentorEntity.getUserEntity().getId()).get();
        MentorResponse mentorResponse = new MentorResponse(userEntity.getName(),userEntity.getSurname(),
                userEntity.getEmail(), userEntity.getPhoneNumber(),
                mentorEntity.getExperience(),mentorEntity.getSubject(),mentorEntity.getSalary(),mentorEntity.getId());
        return ResponseEntity.ok(mentorResponse);
    }

    public ResponseEntity<List<MentorResponse>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<UserEntity> all = userRepository.findAllByRole(UserRole.MENTOR,pageable).getContent();
        List<MentorResponse> responses = new ArrayList<>();
        for (UserEntity userEntity : all) {
            MentorResponse map = modelMapper.map(userEntity, MentorResponse.class);
            MentorInfo mentorInfo = mentorRepository.findMentorInfoByUserEntityId(userEntity.getId()).get();
            map.setSubject(mentorInfo.getSubject());
            map.setSalary(mentorInfo.getSalary());
            map.setExperience(mentorInfo.getExperience());
            map.setId(mentorInfo.getId());
            responses.add(map);
        }
        return ResponseEntity.ok(responses);
    }



    public ResponseEntity<String> deleteByID(UUID mentorId) {
        try {
            MentorInfo mentorInfo = mentorRepository.findById(mentorId).get();
            if(Objects.equals(mentorInfo,null)){
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            }
            mentorRepository.deleteById(mentorId);
            userRepository.deleteById(mentorInfo.getUserEntity().getId());
                return ResponseEntity.status(HttpStatus.OK).body("Deleted");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting");
        }
    }

    public ResponseEntity<List<MentorResponse>> getMentorByCourseId(UUID courseId) {
        CourseEntity courseEntity = courseRepository.findById(courseId).get();
        List<MentorInfo> mentorInfos = mentorRepository.findAllBySubject(courseEntity.getSubject());
        List<MentorResponse> mentorResponses = new ArrayList<>();
        for (MentorInfo mentorInfo : mentorInfos) {
            MentorResponse mentorResponse = modelMapper.map(mentorInfo, MentorResponse.class);
            UserEntity userEntity = userRepository.findById(mentorInfo.getUserEntity().getId()).get();
            mentorResponse.setName(userEntity.getName());
            mentorResponse.setEmail(userEntity.getEmail());
            mentorResponse.setSurname(userEntity.getSurname());
            mentorResponse.setPhoneNumber(userEntity.getPhoneNumber());
            mentorResponses.add(mentorResponse);
        }
        return ResponseEntity.ok(mentorResponses);

    }
}
