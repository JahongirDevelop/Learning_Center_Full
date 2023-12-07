package uz.pdp.learning_center_full.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.AttendanceEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, UUID> {
    List<AttendanceEntity> findAllByStudentId(UUID id);

    List<AttendanceEntity> findAllByGroupId(UUID groupId);
    List<AttendanceEntity> findAllByLessonId(UUID lessonId);
}
