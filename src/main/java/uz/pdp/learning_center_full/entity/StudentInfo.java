package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo extends BaseEntity {
    private Integer rating;
    private UUID groupId;
    @OneToOne
    private UserEntity userEntity;


}
