package org.turkcell.ecommercepair5.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @Email(message = "Please enter a valid email address!")
//    @NotNull(message = "Email cannot be null!")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

//    @NotNull(message = "Password cannot be null!")
//    @Size(min = 8, max = 64, message = "Password must be at least 8 characters long!")
    @Column(name = "password", nullable = false, length = 64)
    private String password;

//    @NotNull(message = "First name cannot be null!")
//    @Size(min = 2)
    @Column(name = "first_name", nullable = false)
    private String firstName;

//    @NotNull(message = "Last name cannot be null!")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
