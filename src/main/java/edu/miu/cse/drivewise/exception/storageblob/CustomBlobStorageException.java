package edu.miu.cse.drivewise.exception.storageblob;

public class CustomBlobStorageException extends RuntimeException {
    public CustomBlobStorageException(String message) {
        super(message);
    }
}
