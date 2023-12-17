package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.GroupStatus;


import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupEntity extends BaseEntity {
    private String groupName;
    @Max(value = 20, message = "20 tadan ko'p bola xonaga sig'maydi")
    private Integer studentCount;
    private UUID courseId;
    private UUID mentorId;
    @Enumerated(EnumType.STRING)
    private GroupStatus status;
    private Integer module;
}
