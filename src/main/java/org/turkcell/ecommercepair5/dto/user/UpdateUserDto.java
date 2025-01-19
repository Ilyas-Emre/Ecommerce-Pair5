package org.turkcell.ecommercepair5.dto.user;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    @Id
    @NotNull(message = "Id cannot be blank!")
    private Integer id;

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
