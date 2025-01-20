package org.turkcell.ecommercepair5.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductDto {
    @NotBlank(message = "At least one ID should be entered to delete!")
    private List<Integer> id;
}
