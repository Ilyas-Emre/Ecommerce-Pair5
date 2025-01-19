package org.turkcell.ecommercepair5.util.exception.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ValidationExceptionResult extends ExceptionResult{

    private List<String> errors;

    public ValidationExceptionResult(List<String> errors) {
        super("ValidationError");
        this.errors = errors;
    }

}
