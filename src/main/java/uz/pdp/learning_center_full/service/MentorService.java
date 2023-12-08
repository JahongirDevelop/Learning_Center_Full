package uz.pdp.learning_center_full.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.MentorCr;
import uz.pdp.learning_center_full.dto.response.MentorResponse;
import uz.pdp.learning_center_full.entity.MentorInfo;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.exception.DuplicateValueException;
import uz.pdp.learning_center_full.repository.MentorRepository;
import uz.pdp.learning_center_full.repository.UserRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final ModelMapper modelMapper;
    private final MentorRepository mentorRepository;
    private final MailSenderService senderService;
    private final UserRepository userRepository;
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
            userRepository.save(userEntity);

            MentorInfo mentorInfo = MentorInfo.builder()
                    .salary(mentorCr.getSalary())
                    .subject(mentorCr.getSubject())
                    .experience(mentorCr.getExperience())
                    .userEntity(userEntity)
                    .build();
            mentorRepository.save(mentorInfo);
        return   ResponseEntity.ok(modelMapper.map(userEntity,MentorResponse.class));
    }


    public ResponseEntity<MentorResponse> getById(UUID mentorID) {
        UserEntity userEntity = userRepository.findById(mentorID).get();
        MentorInfo mentorEntity = mentorRepository.findMentorInfoByUserEntityId(mentorID)
                .orElseThrow(() -> new DataNotFoundException("Invalid value"));
        MentorResponse mentorResponse = new MentorResponse(userEntity.getName(),userEntity.getSurname(),
                userEntity.getEmail(), userEntity.getPhoneNumber(),
                mentorEntity.getExperience(),userEntity.getId());
        return ResponseEntity.ok(mentorResponse);
    }

    public ResponseEntity<List<MentorResponse>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<UserEntity> all = userRepository.findAllByRole(UserRole.MENTOR,pageable).getContent();
        return ResponseEntity.ok(modelMapper.map(all, new TypeToken<List<MentorResponse>>() {}.getType()));
    }
//
//    public ResponseEntity<String> deleteByID(UUID mentorID) {
//        mentorRepository
//                .findById(mentorID)
//                .orElseThrow( () -> new DataNotFoundException("Invalid value"));
//        mentorRepository.deleteById(mentorID);
//        return ResponseEntity.ok("Deleted");
//    }
//
//    public ResponseEntity<MentorResponse> update(UUID mentorId, MentorCr mentorCr) {
//        MentorEntity mentorEntity = mentorRepository
//                .findById(mentorId)
//                .orElseThrow( () -> new DataNotFoundException("Invalid value"));
//        modelMapper.map(mentorCr,mentorEntity);
//        mentorRepository.save(mentorEntity);
//        return ResponseEntity.ok(modelMapper.map(mentorEntity,MentorResponse.class));
//
//    }

}
