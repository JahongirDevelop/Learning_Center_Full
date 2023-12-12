package uz.pdp.learning_center_full.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.CourseEntity;
import uz.pdp.learning_center_full.entity.enums.Subject;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    List<CourseEntity> findBySubject(Subject subject);
}
