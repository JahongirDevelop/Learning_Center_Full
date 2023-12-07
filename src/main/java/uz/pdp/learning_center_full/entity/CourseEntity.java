package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private int module;

}
