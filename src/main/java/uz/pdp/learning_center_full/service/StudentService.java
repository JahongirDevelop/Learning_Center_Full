package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.StudentCR;
import uz.pdp.learning_center_full.entity.StudentInfo;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.repository.GroupRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
private final GroupRepository groupRepository;

//    public void createStudent(StudentCR studentCR, int rating, UUID groupId) {
//        if (!groupRepository.existsById(studentCR.getGroupId())){
//            throw new DataNotFoundException("Group not found by this id " + studentCR.getGroupId());
//        }
//        UserEntity userEntity = UserEntity.builder()
//                .name(studentCR.getName())
//                .surname(studentCR.getSurname())
//                .email(studentCR.getEmail())
//                .password(studentCR.get.encode(password)) // Şifrelenmiş olarak sakla
//                .phoneNumber(phoneNumber)
//                .role(UserRole.STUDENT)
//                .build();
//
//        // UserEntity veritabanına kaydedilir
//        userEntityRepository.save(userEntity);
//
//        // Ardından StudentInfo yaratılır ve ilişkilendirilmiş UserEntity atanır
//        StudentInfo studentInfo = StudentInfo.builder()
//                .rating(rating)
//                .groupId(groupId)
//                .userEntity(userEntity)
//                .build();
//
//        // StudentInfo veritabanına kaydedilir
//        studentInfoRepository.save(studentInfo);
//    }
}

