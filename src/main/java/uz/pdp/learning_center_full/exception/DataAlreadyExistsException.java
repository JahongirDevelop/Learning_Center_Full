package uz.pdp.learning_center_full.exception;

public class DataAlreadyExistsException extends RuntimeException {
    public DataAlreadyExistsException(String msg) {
        super(msg);
    }
}
