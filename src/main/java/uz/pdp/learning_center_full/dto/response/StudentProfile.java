package uz.pdp.learning_center_full.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentProfile {
    private String name;
    private String surname;
    private String groupName;
    private String courseName;
    private String mentorName;
    private String mentorSurname;
    private Integer rating;
}
