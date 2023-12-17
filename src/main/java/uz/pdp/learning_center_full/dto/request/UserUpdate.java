package uz.pdp.learning_center_full.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.Subject;
import uz.pdp.learning_center_full.entity.enums.UserRole;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdate {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
}
