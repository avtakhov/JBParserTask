package parser;

public class UnexpectedSymbolException extends ParserException {
    public UnexpectedSymbolException(String message) {
        super(message);
    }
}
