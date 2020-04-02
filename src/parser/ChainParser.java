package parser;

import expression.And;
import expression.Expression;
import expression.ReturnType;
import expression.TypeException;

import java.util.Objects;

public class ChainParser {
    private final ExpressionParser parser = new ExpressionParser();

    private final String MAP = "map";
    private final String FILTER = "filter";

    private static void assertEquals(Object first, Object second) {
        assert Objects.equals(first, second);
    }

    public String simplify(String s) throws ParserException {
        String[] tokens = s.split("%>%");

        Expression filter = parser.parse("(0=0)");
        Expression map = parser.parse("element");

        for (String token : tokens) {
            String tmp = token.startsWith(MAP) ? MAP : FILTER;
            try {
                assert tmp.length() < token.length();
                assertEquals(token.charAt(tmp.length()), '{');
                assertEquals(token.charAt(token.length() - 1), '}');
            } catch (AssertionError e) {
                throw new FunctionCallException("use '{' and '}' with " + tmp + " call");
            }

            Expression expr = parser.parse(token.substring(tmp.length() + 1, token.length() - 1));

            if (token.startsWith(MAP)) {
                try {
                    assertEquals(ReturnType.INT, expr.getReturnType());
                } catch (AssertionError e) {
                    throw new TypeException("TYPE ERROR");
                }
                map = expr.substitution(map);
            } else if (token.startsWith("filter")) {
                try {
                    assertEquals(ReturnType.BOOL, expr.getReturnType());
                } catch (AssertionError e) {
                    throw new TypeException("TYPE ERROR");
                }

                expr = expr.substitution(map);
                filter = new And(filter, expr);
            } else {
                throw new FunctionCallException("unknown call");
            }
        }

        return "filter{" + filter.toString() + "}" + "%>%" + "map{" + map.toString() + "}";
    }
}
