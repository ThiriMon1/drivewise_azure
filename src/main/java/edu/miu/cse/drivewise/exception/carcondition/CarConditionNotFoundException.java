package edu.miu.cse.drivewise.exception.carcondition;

public class CarConditionNotFoundException extends RuntimeException {
    public CarConditionNotFoundException(String message) {
        super(message);
    }
}
