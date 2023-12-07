package uz.pdp.learning_center_full.dto.request;


import jakarta.validation.constraints.Email;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.Subject;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MentorCr {
    private String name;
    private String surname;
    @Email
    private String email;
    private String phoneNumber;
    private int experience;
    private Double salary;
    private Subject subject;
}
