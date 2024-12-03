package edu.miu.cse.drivewise.exception.offer;

public class WrongOfferStatusException extends RuntimeException {
    public WrongOfferStatusException(String message) {
        super(message);
    }
}
