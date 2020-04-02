package parser;

import expression.Expression;
import parser.exception.ParserException;

public interface Parser {
    Expression parse(String s) throws ParserException;
}
