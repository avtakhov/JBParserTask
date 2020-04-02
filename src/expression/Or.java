package expression;

import base.Triple;

public class Or extends AbstractBinaryExpression {
    public Or(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.BOOL, ReturnType.BOOL));
    }

    @Override
    String getStringValue() {
        return "|";
    }
}
