package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.StudentCR;
import uz.pdp.learning_center_full.dto.response.*;
import uz.pdp.learning_center_full.dto.response.StudentResponse;
import uz.pdp.learning_center_full.entity.*;
import uz.pdp.learning_center_full.entity.enums.ApplicationStatus;
import uz.pdp.learning_center_full.entity.enums.Subject;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.exception.BadRequestException;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.exception.DuplicateValueException;
import uz.pdp.learning_center_full.repository.ApplicationRepository;
import uz.pdp.learning_center_full.repository.GroupRepository;
import uz.pdp.learning_center_full.repository.StudentRepository;
import uz.pdp.learning_center_full.repository.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final CourseService courseService;
    private final MailSenderService senderService;
    private final ModelMapper modelMapper;
    private final  GroupService groupService;
    private final MentorService mentorService;

    public StudentResponse create(StudentCR studentCR) {

        if (!groupRepository.existsById(studentCR.getGroupId())) {
            throw new DataNotFoundException("Group not found by this id " + studentCR.getGroupId());
        } else {
            GroupEntity groupEntity = groupRepository.findById(studentCR.getGroupId()).get();
            if (checkStudentIsExist(studentCR)) {
                throw new DuplicateValueException("Student already exist by these values!");
            }
            if (groupEntity.getStudentCount() == 20) {
                throw new BadRequestException("the number of students is max 20. You cannot add more than 20 students to this group");
            } else {
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

                groupEntity.setStudentCount(groupEntity.getStudentCount() + 1);
                groupRepository.save(groupEntity);
                StudentInfo studentInfo = StudentInfo.builder()
                        .rating(0)
                        .groupId(studentCR.getGroupId())
                        .userEntity(userEntity)
                        .build();
                studentRepository.save(studentInfo);
                StudentResponse studentResponse = new StudentResponse(0, userEntity.getName(),userEntity.getSurname(),
                        userEntity.getPhoneNumber(), userEntity.getEmail(), studentCR.getGroupId(),studentInfo.getId());
                return studentResponse;
            }
        }
    }

    public ResponseEntity<StudentResponse> createByApplication(UUID applicationID, UUID groupID) {
        ApplicationEntity application1 = applicationRepository.findById(applicationID)
                .orElseThrow(() -> new DataNotFoundException("Application not found with this id " + applicationID));
        application1.setStatus(ApplicationStatus.CHECKED);
        applicationRepository.save(application1);
        GroupEntity groupEntity = groupRepository.findById(groupID).get();
        if(!applicationRepository.existsById(applicationID)) {
            throw new DataNotFoundException("Application not found by this id " + applicationID);
        } else if (!groupRepository.existsById(groupID)){
            throw new DataNotFoundException("Group not found by this id " + groupID);
        } else {
            ApplicationEntity application = applicationRepository.findById(applicationID).get();
            StudentCR studentCR = modelMapper.map(application, StudentCR.class);
            studentCR.setGroupId(groupID);
            if (checkStudentIsExist(studentCR)) {
                throw new DuplicateValueException("Student already exist by these values!");
            }
            if (groupEntity.getStudentCount() == 20) {
                throw new BadRequestException("the number of students is max 20. You cannot add more than 20 students to this group");
            } else {
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

                groupEntity.setStudentCount(groupEntity.getStudentCount() + 1);
                StudentInfo studentEntity = modelMapper.map(studentCR, StudentInfo.class);
                studentEntity.setUserEntity(save);
                studentEntity.setRating(0);

                StudentResponse studentResponse = new StudentResponse(0, userEntity.getName(),
                        userEntity.getSurname(), userEntity.getPhoneNumber(), userEntity.getEmail(),
                        studentCR.getGroupId(), studentEntity.getId());

                Optional<GroupEntity> updateGroup = groupRepository.findById(studentEntity.getGroupId());
                updateGroup.get().setStudentCount(updateGroup.get().getStudentCount());
                groupRepository.save(modelMapper.map(updateGroup, GroupEntity.class));
                studentRepository.save(studentEntity);
                return ResponseEntity.ok(studentResponse);
            }
        }
    }

    public List<StudentResponse> getAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    List<UserEntity> all = userRepository.findAllByRole(UserRole.STUDENT,pageable).getContent();
    List<StudentResponse> responses = new ArrayList<>();
        for (UserEntity userEntity : all) {
            StudentResponse studentResponse = modelMapper.map(userEntity, StudentResponse.class);
            StudentInfo studentInfo = studentRepository.findStudentInfoByUserEntityId(userEntity.getId()).get();
            studentResponse.setId(studentInfo.getId());
            studentResponse.setGroupId(studentInfo.getGroupId());
            responses.add(studentResponse);
        }
        return responses;
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

    public List<StudentResponse> getByGroupId(UUID groupId) {
        List<StudentInfo> studentInfoList = studentRepository.findAllByGroupId(groupId);
        List<StudentResponse> studentResponses = studentInfoList.stream()
                .map(student -> new StudentResponse(
                        student.getRating(),
                        student.getUserEntity().getName(),
                        student.getUserEntity().getSurname(),
                        student.getUserEntity().getPhoneNumber(),
                        student.getUserEntity().getEmail(),
                        student.getGroupId(),
                        student.getUserEntity().getId()
                ))
                .collect(Collectors.toList());
        return studentResponses;

    }
    public List<StudentResponse> getStudentByRating(UUID groupId){
        List<StudentInfo> students = studentRepository.findAllByGroupId(groupId);
        List<UserEntity> userEntities = new ArrayList<>();
        for (StudentInfo student : students) {
            userEntities.add(student.getUserEntity());
        }
        List<StudentResponse> responses = modelMapper.map(userEntities, new TypeToken<List<StudentResponse>>() {}.getType());
        for (StudentResponse respons : responses) {
            for (StudentInfo student : students) {
                if(student.getUserEntity().getId().equals(respons.getId())){
            respons.setRating(student.getRating());
            respons.setGroupId(student.getGroupId());}
            }
        }

        studentRepository.findAllByGroupId(groupId,Sort.by(Sort.Direction.ASC, "rating"));
        return responses;

    }

    public ResponseEntity<StudentResponse> getById(UUID studentId) {
        StudentInfo studentEntity = studentRepository.findStudentInfoByUserEntityId(studentId)
                .orElseThrow(() -> new DataNotFoundException("Invalid value"));
        UserEntity userEntity = userRepository.findById(studentEntity.getUserEntity().getId()).get();
        StudentResponse studentresponse = new StudentResponse(studentEntity.getRating(),userEntity.getName(),
                userEntity.getSurname(), userEntity.getPhoneNumber(), userEntity.getEmail(),
                studentEntity.getGroupId(),studentEntity.getId());
        return ResponseEntity.ok(studentresponse);
    }


}

