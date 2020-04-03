package parser;

import base.Asserts;
import expression.And;
import expression.Expression;
import expression.ReturnType;
import expression.TypeException;

import java.util.Map;
import java.util.Objects;

public class ChainParser {
    private final ExpressionParser parser = new ExpressionParser();

    private final String MAP = "map";
    private final String FILTER = "filter";

    private final Map<String, ReturnType> EXPECT_TYPE = Map.of(
            MAP, ReturnType.INT,
            FILTER, ReturnType.BOOL
    );

    public String simplify(String s) throws ParserException {
        String[] tokens = s.split("%>%");

        Expression filter = parser.parse("(0=0)");
        Expression map = parser.parse("element");

        for (String token : tokens) {
            int left = token.indexOf('{');
            int right = token.lastIndexOf('}');
            if (right == -1 || left == -1 || right != token.length() - 1) {
                throw new FunctionCallException("{} error");
            }
            Expression expr = parser.parse(token.substring(left + 1, right));

            String tmp = token.substring(0, left);

            if (!EXPECT_TYPE.containsKey(tmp)) {
                throw new FunctionCallException("unknown function called");
            }
            if (expr.getReturnType() != EXPECT_TYPE.get(tmp)) {
                throw new TypeException(tmp + " must return return " + EXPECT_TYPE.get(tmp));
            }

            switch (tmp) {
                case MAP:
                    map = expr.substitution(map);
                    break;
                case FILTER:
                    expr = expr.substitution(map);
                    filter = new And(filter, expr);
                    break;
            }
        }

        return "filter{" + filter.toString() + "}" + "%>%" + "map{" + map.toString() + "}";
    }

    private void checkBrackets(String token, String tmp) throws FunctionCallException {
        try {
            Asserts.assertEquals("'{' error", token.charAt(tmp.length()), '{');
            Asserts.assertEquals("'}' error", token.charAt(token.length() - 1), '}');
        } catch (AssertionError e) {
            throw new FunctionCallException("use '{' and '}' with " + tmp + " call:" + e.getMessage());
        }

    }
}
