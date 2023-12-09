package uz.pdp.learning_center_full.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.StudentInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentInfo, UUID> {
   // boolean existsByEmail(String email);
    List<StudentInfo> findAllByGroupId(UUID groupId);

    List<StudentInfo> findAllByGroupId(UUID groupId, Sort sort  );

    Optional<StudentInfo> findStudentInfoByUserEntityId(UUID id);



    //List<StudentInfo> findByName(String name);
//    ResponseEntity<List<StudentInfo>> findAllByGroupId(Pageable pageable,UUID groupID);
}
