package uz.pdp.learning_center_full.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.ApplicationEntity;
import uz.pdp.learning_center_full.entity.enums.ApplicationStatus;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, UUID> {

    Page<ApplicationEntity> findAllByStatus(Pageable pageable, ApplicationStatus status);

    boolean existsByEmail(String email);

    boolean existsByCourseId(UUID courseId);
}
