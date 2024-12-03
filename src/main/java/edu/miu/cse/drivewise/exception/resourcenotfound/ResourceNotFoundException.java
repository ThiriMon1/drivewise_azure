package edu.miu.cse.drivewise.exception.resourcenotfound;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
