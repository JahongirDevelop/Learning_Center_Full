package uz.pdp.learning_center_full.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_center_full.entity.UserEntity;

import java.util.List;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

//    Optional<UserEntity> findAllByRole(Pageable pageable,UserRole role);
    Page<UserEntity> findAllByRole(UserRole userRole,Pageable pageable);

    List<UserEntity> findByName(String name);

}
