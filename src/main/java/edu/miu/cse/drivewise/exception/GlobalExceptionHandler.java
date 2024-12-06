package edu.miu.cse.drivewise.exception;

import edu.miu.cse.drivewise.exception.carReview.CarReviewNotFoundException;
import edu.miu.cse.drivewise.exception.carcondition.CarConditionNotFoundException;
import edu.miu.cse.drivewise.exception.carmodel.CarModelNotFoundException;
import edu.miu.cse.drivewise.exception.customer.CustomerNotFoundException;
import edu.miu.cse.drivewise.exception.inventory.DuplicateVINException;
import edu.miu.cse.drivewise.exception.inventory.FailedFiledUpload;
import edu.miu.cse.drivewise.exception.make.MakeNotFoundException;
import edu.miu.cse.drivewise.exception.offer.*;
import edu.miu.cse.drivewise.exception.resourcenotfound.ResourceNotFoundException;
import edu.miu.cse.drivewise.exception.storageblob.CustomBlobStorageException;
import edu.miu.cse.drivewise.exception.user.DuplicateEmailException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    String errorMessage = fieldError.getDefaultMessage();
                    String fieldName = fieldError.getField();
                    errors.put(fieldName,errorMessage);
                }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());//check with prof's code
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateVINException.class)
    public ResponseEntity<String> handleDuplicateVINException(DuplicateVINException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(MakeNotFoundException.class)
    public ResponseEntity<String> handleMakeNotFoundException(MakeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CarConditionNotFoundException.class)
    public ResponseEntity<String> handleCarConditionNotFoundException(CarConditionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CarModelNotFoundException.class)
    public ResponseEntity<String> handleCarModelNotFoundException(CarModelNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CarReviewNotFoundException.class)
    public ResponseEntity<String> handleCarReviewNotFoundException(CarReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(OfferAlreadyCancelledException.class)
    public ResponseEntity<String> handleOfferAlreadyCancelledException(OfferAlreadyCancelledException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(OfferCannotCancelException.class)
    public ResponseEntity<String> handleOfferCannotCancelException(OfferCannotCancelException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(InventoryIsNotAvailableException.class)
    public ResponseEntity<String> handleInventoryIsNotAvailableException(InventoryIsNotAvailableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(WrongOfferStatusException.class)
    public ResponseEntity<String> handleWrongOfferStatusException(WrongOfferStatusException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(OfferCannotFinalizeSaleException.class)
    public ResponseEntity<String> handleOfferCannotFinalizeSaleException(OfferCannotFinalizeSaleException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(FailedFiledUpload.class)
    public ResponseEntity<String> handleFailedFiledUpload(FailedFiledUpload e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(CustomBlobStorageException.class)
    public ResponseEntity<String> handleCustomBlobStorageException(CustomBlobStorageException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
