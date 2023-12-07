package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.Subject;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MentorInfo extends BaseEntity {
    private int experience;
    private Double salary;
    @Enumerated(EnumType.STRING)
    private Subject subject;
    @OneToOne
    private UserEntity userEntity;
}
