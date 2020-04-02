package expression;

import base.Triple;

public class And extends AbstractBinaryExpression {
    public And(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.BOOL, ReturnType.BOOL));
    }

    @Override
    String getStringValue() {
        return "&";
    }
}
