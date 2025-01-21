package food_delivery.exception;

import food_delivery.enumeration.ApplicationErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private final ApplicationErrorEnum applicationErrorEnum;

    public BusinessException(ApplicationErrorEnum applicationErrorEnum ) {
        super(applicationErrorEnum.getMessage());
        this.applicationErrorEnum= applicationErrorEnum;
    }
}