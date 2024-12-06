package edu.miu.cse.drivewise.exception.inventory;

public class DuplicateVINException extends RuntimeException {
    public DuplicateVINException(String message) {
        super(message);
    }
}
