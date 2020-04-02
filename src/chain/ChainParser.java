package chain;

import expression.And;
import expression.Expression;
import expression.Or;
import expression.ReturnType;
import parser.ExpressionParser;
import parser.Parser;
import parser.exception.ParserException;

import java.util.Objects;

public class ChainParser {
    ExpressionParser parser = new ExpressionParser();

    private static void assertEquals(Object first, Object second, String message) {
        if (!Objects.equals(first, second)) {
            throw new RuntimeException(message);
        }
    }

    public String simplify(String s) throws ParserException {
        String[] tokens = s.split("%>%");
        Expression filter = parser.parse("(0=0)");
        Expression map = parser.parse("element");
        for (String token : tokens) {
            if (token.startsWith("map")) {
                assertEquals(token.charAt(3), '{', "SYNTAX ERROR");
                assertEquals(token.charAt(token.length() - 1), '}', "SYNTAX ERROR");
                Expression expr = parser.parse(token.substring(4, token.length() - 1));
                assertEquals(expr.getReturnType(), ReturnType.INT, "TYPE ERROR");
                map = expr.substitution(map);
            } else if (token.startsWith("filter")) {
                assertEquals(token.charAt(6), '{', "SYNTAX ERROR");
                assertEquals(token.charAt(token.length() - 1), '}', "SYNTAX ERROR");
                Expression expr = parser.parse(token.substring(7, token.length() - 1));
                assertEquals(expr.getReturnType(), ReturnType.BOOL, "TYPE ERROR");
                expr = expr.substitution(map);
                filter = new And(filter, expr);
            } else {
                return "SYNTAX ERROR";
            }
        }
        return "filter{"+ filter.toString() + "}" + "%>%" + "map{" + map.toString() + "}";
    }
}
