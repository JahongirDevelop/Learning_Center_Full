package uz.pdp.learning_center_full.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthDto {
    @NotBlank(message = "email must not be blank")
    private String email;
    @NotBlank(message = "password must not be blank")
    private String password;
}

