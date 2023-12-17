package uz.pdp.learning_center_full.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.ApplicationEntity;
import uz.pdp.learning_center_full.entity.LessonEntity;
import uz.pdp.learning_center_full.entity.enums.ApplicationStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    List<LessonEntity> findLessonEntitiesByGroupIdAndModule(UUID groupId,Integer module);
    Page<LessonEntity> findAllByGroupId(Pageable pageable, UUID groupId);


    List<LessonEntity> findLessonEntitiesByModule(Integer module);

    List<LessonEntity> findLessonEntitiesByGroupId(UUID groupId);
}
