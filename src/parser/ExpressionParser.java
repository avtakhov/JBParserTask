package parser;

import expression.*;

import java.util.Set;

public class ExpressionParser extends BaseParser {

    private final String ELEMENT = "element";

    private static Set<String> operations = Set.of(
            "+",
            "-",
            "*",
            ">",
            "<",
            "&",
            "|",
            "="
    );

    public Expression parse(String s) throws ParserException {
        setSource(new StringSource(s));
        Expression ans = parseExpression();
        if (hasNext()) {
            throw new UnexpectedSymbolException("Cannot parse after " + getCh());
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
                throw new UnknownTokenException("Invalid operator");
        }
    }

    private String parseOperation() throws ParserException {
        for (String op : operations) {
            if (test(op)) {
                return op;
            }
        }
        throw new UnknownTokenException("Invalid operator");
    }

    private Expression parseSimple() throws ParserException {
        if (test(ELEMENT)) {
            return new Element();
        } else {
            return parseConstant();
        }
    }

    private Const parseConstant() throws ParserException {
        StringBuilder sb = new StringBuilder();
        if (test('-')) {
            sb.append('-');
        }
        while (Character.isDigit(getCh())) {
            sb.append(getCh());
            nextChar();
        }
        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw new ConstantException("Incorrect integer " + sb.toString());
        }
    }
}
