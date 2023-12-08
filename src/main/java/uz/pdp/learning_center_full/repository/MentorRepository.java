package uz.pdp.learning_center_full.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.MentorInfo;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentorRepository extends JpaRepository<MentorInfo, UUID> {
    Optional<MentorInfo> findMentorInfoByUserEntityId(UUID uuid);


    void deleteMentorInfoByUserEntityId(UUID id);
}
