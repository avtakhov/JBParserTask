package expression;

import base.Triple;

public class Subtract extends AbstractBinaryExpression {
    public Subtract(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.INT, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return "-";
    }
}
