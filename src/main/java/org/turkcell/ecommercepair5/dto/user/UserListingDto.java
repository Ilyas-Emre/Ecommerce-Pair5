package org.turkcell.ecommercepair5.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListingDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean isActive;

}
