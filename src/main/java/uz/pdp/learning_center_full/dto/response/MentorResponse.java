package uz.pdp.learning_center_full.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.learning_center_full.entity.enums.Subject;


import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MentorResponse {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private int experience;
//    private Double salary;
//    private Subject subject;
    private UUID id;
}
