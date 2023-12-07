package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo extends BaseEntity {
    private int rating;
    private UUID groupId;

    @OneToOne
//    @JoinColumn()
    private UserEntity userEntity;

    @ManyToOne
    private UserEntity parent;
}
