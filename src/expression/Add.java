package expression;

import base.Triple;

public class Add extends AbstractBinaryExpression {
    public Add(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.INT, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return "+";
    }
}
