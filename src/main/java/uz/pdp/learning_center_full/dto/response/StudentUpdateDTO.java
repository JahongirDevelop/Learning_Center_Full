package uz.pdp.learning_center_full.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentUpdateDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;

}
