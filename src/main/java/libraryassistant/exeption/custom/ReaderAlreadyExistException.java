package libraryassistant.exeption.custom;

public class ReaderAlreadyExistException extends RuntimeException {
    public ReaderAlreadyExistException(String message) {
        super(message);
    }
}