package expression;

import base.Triple;

public class Less extends AbstractBinaryExpression {
    public Less(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return "<";
    }
}
