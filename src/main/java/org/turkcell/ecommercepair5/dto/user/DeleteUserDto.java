package org.turkcell.ecommercepair5.dto.user;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserDto {

    @NotBlank(message = "At least one ID should be entered to delete!")

    private List<Integer> id;

}
