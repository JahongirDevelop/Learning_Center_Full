package uz.pdp.learning_center_full.dto.request;

import lombok.*;
import uz.pdp.learning_center_full.entity.enums.GroupStatus;


import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateGroupDto {

    private String groupName;
    private UUID courseId;
    private UUID mentorId;
    private Integer studentCount;
    private GroupStatus status;

}
