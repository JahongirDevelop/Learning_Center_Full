package uz.pdp.learning_center_full.dto.request;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseCr {
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private int module;
}
