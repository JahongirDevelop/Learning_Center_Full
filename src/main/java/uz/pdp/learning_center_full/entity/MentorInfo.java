package uz.pdp.learning_center_full.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.Subject;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MentorInfo extends BaseEntity {
    private Integer experience;
    private Double salary;
    @Enumerated(EnumType.STRING)
    private Subject subject;
    @OneToOne
    private UserEntity userEntity;
}
