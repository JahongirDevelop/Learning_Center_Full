package uz.pdp.learning_center_full.dto.response;


import lombok.*;
import uz.pdp.learning_center_full.entity.enums.ApplicationStatus;


import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationResponse {


    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private ApplicationStatus applicationStatus;
    private UUID courseId;
    private UUID id;


}
