package exception;

/**
 *
 * @author Gabriel Reis Miranda
 */
public class ConnectErrorException extends Exception {

    /**
     * Creates a new instance of <code>ConnetErrorException</code> without
     * detail message.
     */
    public ConnectErrorException() {
        super("An error occurred while trying to connect");
    }

    /**
     * Constructs an instance of <code>ConnetErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ConnectErrorException(String msg) {
        super(msg);
    }
}
