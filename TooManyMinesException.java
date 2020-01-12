public class TooManyMinesException extends Exception
{
    private static final long serialVersionUID = 1L;
    public TooManyMinesException(final String errorMessage) {
        super(errorMessage);
    }
}