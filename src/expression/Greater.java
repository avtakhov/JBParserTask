package expression;

import base.Triple;

public class Greater extends AbstractBinaryExpression {
    public Greater(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return ">";
    }
}
