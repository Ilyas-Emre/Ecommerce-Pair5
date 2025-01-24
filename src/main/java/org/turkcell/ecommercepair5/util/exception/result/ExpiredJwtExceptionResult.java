package org.turkcell.ecommercepair5.util.exception.result;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpiredJwtExceptionResult extends ExceptionResult {

    private String errorMessage = "Your session has expired";

    public ExpiredJwtExceptionResult(String errorMessage){
        super("ExpiredJwtExceptionResult");
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
