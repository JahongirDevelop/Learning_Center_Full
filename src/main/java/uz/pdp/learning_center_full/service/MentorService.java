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
import uz.pdp.learning_center_full.exception.DuplicateValueException;
import uz.pdp.learning_center_full.repository.MentorRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final ModelMapper modelMapper;
    private final MentorRepository mentorRepository;
//    private final MailSenderService senderService;
//    public ResponseEntity<MentorResponse> addMentor(MentorCr mentorCr) {
//        if(mentorRepository.existsByEmailOrPhoneNumber(mentorCr.getEmail(),mentorCr.getPhoneNumber())){
//            throw new DuplicateValueException("Mentor already exist!");
//        }else {
//            String verificationCode = senderService.sendVerificationCode(mentorCr.getEmail());;
//            MentorEntity mentorEntity = modelMapper.map(mentorCr, MentorEntity.class);
//            mentorEntity.setPassword(verificationCode);
//            mentorEntity.setRole(UserRole.MENTOR);
//            mentorRepository.save(mentorEntity);
//            System.out.println("verificationCode = " + verificationCode);
//            return ResponseEntity.ok(modelMapper.map(mentorEntity,MentorResponse.class));
//        }
//    }
//
//    public ResponseEntity<MentorResponse> getById(UUID mentorID) {
//        MentorEntity mentorEntity = mentorRepository.findById(mentorID)
//                .orElseThrow(() -> new DataNotFoundException("Invalid value"));
//        return ResponseEntity.ok(modelMapper.map(mentorEntity,MentorResponse.class));
//    }
//
//    public ResponseEntity<List<MentorResponse>> getAll(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        List<MentorEntity> all = mentorRepository.findAll(pageable).getContent();
//        return ResponseEntity.ok(modelMapper.map(all, new TypeToken<List<MentorResponse>>() {}.getType()));
//    }
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
