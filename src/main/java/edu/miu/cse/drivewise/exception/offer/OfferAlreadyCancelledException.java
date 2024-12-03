package edu.miu.cse.drivewise.exception.offer;

public class OfferAlreadyCancelledException extends RuntimeException {
    public OfferAlreadyCancelledException(String message) {
        super(message);
    }
}
