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
public class CourseResponse {
    private Subject subject;
    private String description;
    private Integer module;
    private UUID id;
}
