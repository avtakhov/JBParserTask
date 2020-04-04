package expression;

import base.Triple;

public class Multiply extends AbstractBinaryExpression {
    public Multiply(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.INT, ReturnType.INT, ReturnType.INT));
    }

    @Override
    protected String getStringValue() {
        return "*";
    }

    @Override
    public ReturnType getReturnType() {
        return ReturnType.INT;
    }

    @Override
    public Polynomial getSimple() {
        Polynomial fp = (Polynomial) first.getSimple();
        Polynomial sp = (Polynomial) second.getSimple();
        return fp.mul(sp);
    }
}
