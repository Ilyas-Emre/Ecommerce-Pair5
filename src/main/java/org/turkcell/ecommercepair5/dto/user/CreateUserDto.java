package org.turkcell.ecommercepair5.dto.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "First name cannot be blank!")
    @Length(min=2, message = "First name must be longer than 2 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank!")
    @Length(min=2, message = "Last name must be longer than 2 characters.")
    private String lastName;

    @Email(message = "Please enter a valid email address!")
    @NotBlank(message = "Email cannot be blank!")
    private String email;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 8, max = 64, message = "Password must be at least 8 characters long!")
    private String password;

}
