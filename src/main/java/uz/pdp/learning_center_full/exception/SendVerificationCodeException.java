package uz.pdp.learning_center_full.exception;


public class SendVerificationCodeException extends RuntimeException{
    public SendVerificationCodeException(String message) {
        super(message);
    }
}