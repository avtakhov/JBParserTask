package expression;

import base.Triple;

public class Multiply extends AbstractBinaryExpression {
    public Multiply(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.INT, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return "*";
    }
}
