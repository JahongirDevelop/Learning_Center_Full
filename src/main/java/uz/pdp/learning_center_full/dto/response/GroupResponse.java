package uz.pdp.learning_center_full.dto.response;

import lombok.*;
import uz.pdp.learning_center_full.entity.enums.GroupStatus;


import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupResponse {
    private String groupName;
    private Integer studentCount;
    private UUID courseId;
    private UUID mentorId;
    private GroupStatus status;
    private UUID id;
    private int module;
}
