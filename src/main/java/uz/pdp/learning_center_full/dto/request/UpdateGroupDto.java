package uz.pdp.learning_center_full.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.GroupStatus;


import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateGroupDto {
    @NotNull
    private String groupName;
    @NotNull
    private UUID mentorId;

}
