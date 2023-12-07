package uz.pdp.learning_center_full.dto.request;

import lombok.*;

import java.util.UUID;
//@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StudentCR {
    private String name;
    private String surname;
    private int rating;
    private String phoneNumber;
    private String email;
    private UUID groupId;
}
