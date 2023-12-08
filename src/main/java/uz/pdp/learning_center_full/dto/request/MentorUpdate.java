package uz.pdp.learning_center_full.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.Subject;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MentorUpdate {
    private String name;
    private String surname;
    private String password;
    @Email
    private String email;
    private String phoneNumber;
    private int experience;
}
