package uz.pdp.learning_center_full.dto.request;

import lombok.*;
import uz.pdp.learning_center_full.entity.enums.UserRole;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class UserCr {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
//    private UserRole userRole ;
}
