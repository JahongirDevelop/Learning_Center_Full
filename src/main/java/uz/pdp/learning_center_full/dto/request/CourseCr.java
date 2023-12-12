package uz.pdp.learning_center_full.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.learning_center_full.entity.enums.Subject;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseCr {
    private Subject subject;
    private String description;
    @Column(nullable = false)
    private Integer module;
}
