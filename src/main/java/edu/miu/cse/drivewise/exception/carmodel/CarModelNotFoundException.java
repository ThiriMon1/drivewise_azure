package edu.miu.cse.drivewise.exception.carmodel;

public class CarModelNotFoundException extends RuntimeException {
    public CarModelNotFoundException(String message) {
        super(message);
    }
}
