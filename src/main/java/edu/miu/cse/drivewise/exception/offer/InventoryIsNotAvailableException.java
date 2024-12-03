package edu.miu.cse.drivewise.exception.offer;

public class InventoryIsNotAvailableException extends RuntimeException {
    public InventoryIsNotAvailableException(String message) {
        super(message);
    }
}
