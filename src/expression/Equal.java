package expression;

import base.Triple;

public class Equal extends AbstractBinaryExpression {
    public Equal(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return "=";
    }
}
