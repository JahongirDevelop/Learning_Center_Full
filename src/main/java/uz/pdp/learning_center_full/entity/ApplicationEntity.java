package uz.pdp.learning_center_full.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.learning_center_full.entity.enums.ApplicationStatus;


import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationEntity extends BaseEntity{
    private String name;
    private String surname;
    private String phoneNumber;
    @Email
    private String email;
    private UUID courseId;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

}
