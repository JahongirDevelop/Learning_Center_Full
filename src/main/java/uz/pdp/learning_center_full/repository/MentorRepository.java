package uz.pdp.learning_center_full.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.MentorInfo;
import uz.pdp.learning_center_full.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentorRepository extends JpaRepository<MentorInfo, UUID> {
    Optional<MentorInfo> findMentorInfoByUserEntityId(UUID uuid);


    void deleteMentorInfoByUserEntity(UserEntity user);

    void deleteByUserEntityId(UUID uuid);
}
