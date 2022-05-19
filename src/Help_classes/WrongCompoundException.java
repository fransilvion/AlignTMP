package Help_classes;

/**
 * Created by Fransilvion on 14.09.2016.
 */
public class WrongCompoundException extends Exception{

    public WrongCompoundException() {
        super();
    }
    public WrongCompoundException(String message) { super(message); }
    public WrongCompoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public WrongCompoundException(Throwable cause) {
        super(cause);
    }

}
