package uz.pdp.learning_center_full.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationCr {
    private String name;
    private String surname;
    private String phoneNumber;
    @Email
    private String email;
    private UUID courseId;


}
