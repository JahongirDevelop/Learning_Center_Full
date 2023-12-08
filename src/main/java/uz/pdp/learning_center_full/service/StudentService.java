package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.StudentCR;
import uz.pdp.learning_center_full.dto.response.StudentResponse;
import uz.pdp.learning_center_full.dto.response.StudentUpdateDTO;
import uz.pdp.learning_center_full.entity.ApplicationEntity;
import uz.pdp.learning_center_full.entity.GroupEntity;
import uz.pdp.learning_center_full.entity.StudentInfo;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.exception.DuplicateValueException;
import uz.pdp.learning_center_full.repository.ApplicationRepository;
import uz.pdp.learning_center_full.repository.GroupRepository;
import uz.pdp.learning_center_full.repository.StudentRepository;
import uz.pdp.learning_center_full.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final MailSenderService senderService;
    private final ModelMapper modelMapper;
    private final ApplicationRepository applicationRepository;

    public StudentResponse create(StudentCR studentCR) {
        if (!groupRepository.existsById(studentCR.getGroupId())) {
            throw new DataNotFoundException("Group not found by this id " + studentCR.getGroupId());
        } else {
            if (checkStudentIsExist(studentCR)) {
                throw new DuplicateValueException("Student already exist by these values!");
            }
            String verificationCode = senderService.sendVerificationCode(studentCR.getEmail());
            UserEntity userEntity = UserEntity.builder()
                    .name(studentCR.getName())
                    .surname(studentCR.getSurname())
                    .email(studentCR.getEmail())
                    .password(verificationCode)
                    .phoneNumber(studentCR.getPhoneNumber())
                    .role(UserRole.STUDENT)
                    .build();
            userRepository.save(userEntity);

            StudentInfo studentInfo = StudentInfo.builder()
                    .rating(studentCR.getRating())
                    .groupId(studentCR.getGroupId())
                    .userEntity(userEntity)
                    .build();
            studentRepository.save(studentInfo);

            return modelMapper.map(userEntity, StudentResponse.class);
        }
    }

    public ResponseEntity<StudentResponse> createByApplication(UUID applicationID, UUID groupID) {
        if(!applicationRepository.existsById(applicationID)) {
            throw new DataNotFoundException("Application not found by this id " + applicationID);
        } else if (!groupRepository.existsById(groupID)){
            throw new DataNotFoundException("Group not found by this id " + groupID);
        } else {
            ApplicationEntity application = applicationRepository.findById(applicationID).get();
            StudentCR studentCR = modelMapper.map(application,StudentCR.class);
            studentCR.setGroupId(groupID);
            if(checkStudentIsExist(studentCR)){
                throw new DuplicateValueException("Student already exist by these values!");
            }
            String verificationCode = senderService.sendVerificationCode(studentCR.getEmail());
            UserEntity userEntity = UserEntity.builder()
                    .phoneNumber(studentCR.getPhoneNumber())
                    .name(studentCR.getName())
                    .email(studentCR.getEmail())
                    .surname(studentCR.getSurname())
                    .password(verificationCode)
                    .role(UserRole.STUDENT)
                    .build();
            UserEntity save = userRepository.save(userEntity);

            StudentInfo studentEntity = modelMapper.map(studentCR, StudentInfo.class);
            studentEntity.setUserEntity(save);

            StudentResponse studentResponse = new StudentResponse(studentCR.getRating(), userEntity.getName(),
                    userEntity.getSurname(), userEntity.getPhoneNumber(), userEntity.getEmail(),
                    studentCR.getGroupId(), userEntity.getId());

            Optional<GroupEntity> updateGroup = groupRepository.findById(studentEntity.getGroupId());
            updateGroup.get().setStudentCount(updateGroup.get().getStudentCount()+1);
            groupRepository.save(modelMapper.map(updateGroup, GroupEntity.class));
             studentRepository.save(studentEntity);
            return ResponseEntity.ok(studentResponse);
        }
    }

    public List<StudentResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<UserEntity> studentEntities = userRepository.findAllByRole(UserRole.STUDENT).getContent();
        return modelMapper.map(studentEntities, new TypeToken<List<StudentResponse>>() {}.getType());
    }

    public StudentResponse updateById(UUID studentId, StudentUpdateDTO update) {
        StudentInfo student = studentRepository
                .findById(studentId)
                .orElseThrow( () -> new DataNotFoundException("student not found"));

        UserEntity userEntity = userRepository.findById(student.getUserEntity().getId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        userEntity.setEmail(update.getEmail());
        userEntity.setName(update.getName());
        userEntity.setPassword(update.getPassword());
        userEntity.setPhoneNumber(update.getPhoneNumber());
        userEntity.setSurname(update.getSurname());
        userEntity.setRole(UserRole.STUDENT);

        student.setUserEntity(userEntity);
        student.setRating(update.getRating());
        student.setGroupId(update.getGroupId());
        studentRepository.save(student);
        return new StudentResponse(student.getRating(), userEntity.getName(),
                userEntity.getSurname(), userEntity.getPhoneNumber(), userEntity.getEmail(),
                student.getGroupId(), userEntity.getId());

    }

    private boolean checkStudentIsExist(StudentCR studentCR){
        List<UserEntity> userEntities = userRepository.findByName(studentCR.getName());
        for (UserEntity entity : userEntities) {
            if (!Objects.equals(null, entity) &&
                    Objects.equals(entity.getName(), studentCR.getName()) &&
                    Objects.equals(entity.getEmail(), studentCR.getEmail())) {
                return  true;
            }
        }return false;
    }
}

