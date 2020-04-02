package parser;

import expression.*;
import parser.exception.ParserException;

import java.util.Set;

public class ExpressionParser extends BaseParser implements Parser {

    Set<String> operations = Set.of(
            "+",
            "-",
            "*",
            ">",
            "<",
            "&",
            "|",
            "="
    );

    @Override
    public Expression parse(String s) throws ParserException {
        setSource(new StringSource(s));
        Expression ans = parseExpression();
        if (hasNext()) {
            throw new ParserException("SYNTAX ERROR");
        }
        return ans;
    }

    private Expression parseExpression() throws ParserException {
        if (getCh() == '(') {
            return parseBinary();
        } else {
            return parseSimple();
        }
    }

    private Expression parseBinary() throws ParserException {
        expect('(');
        Expression first = parseExpression();
        String op = parseOperation();
        Expression second = parseExpression();
        expect(')');
        switch (op) {
            case "+":
                return new Add(first, second);
            case "-":
                return new Subtract(first, second);
            case "*":
                return new Multiply(first, second);
            case "|":
                return new Or(first, second);
            case "&":
                return new And(first, second);
            case "=":
                return new Equal(first, second);
            case "<":
                return new Less(first, second);
            case ">":
                return new Greater(first, second);
            default:
                throw new ParserException("SYNTAX ERROR");
        }
    }

    private String parseOperation() throws ParserException {
        for (int i = BUFFER_SIZE; i > 0; i--) {
            String s = String.copyValueOf(buffer, 0, i);
            if (operations.contains(s)) {
                skipChars(s.length());
                return s;
            }
        }
        throw new ParserException("SYNTAX ERROR");
    }

    private Expression parseSimple() throws ParserException {
        if (String.copyValueOf(buffer, 0, 7).equals("element")) {
            skipChars(7);
            return new Element();
        } else {
            return parseConstant();
        }
    }

    private Const parseConstant() throws ParserException {
        StringBuilder sb = new StringBuilder();
        if (getCh() == '-') {
            sb.append(getCh());
            nextChar();
        }
        while (Character.isDigit(getCh())) {
            sb.append(getCh());
            nextChar();
        }
        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw new ParserException("SYNTAX ERROR");
        }
    }
}
