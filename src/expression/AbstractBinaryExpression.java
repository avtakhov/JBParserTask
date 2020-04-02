package expression;

import base.Triple;
import expression.exception.TypeException;

public abstract class AbstractBinaryExpression implements Expression {

    private Expression first;
    private Expression second;
    private final ReturnType returnType;

    protected AbstractBinaryExpression(Expression first, Expression second, Triple<ReturnType, ReturnType, ReturnType> expectType) {
        this.first = first;
        this.second = second;
        if (expectType.second() != first.getReturnType() || expectType.third() != second.getReturnType()) {
            throw new TypeException("TYPE ERROR");
        }
        this.returnType = expectType.first();
    }

    abstract String getStringValue();

    @Override
    public Expression substitution(Expression expr) {
        first = first.substitution(expr);
        second = second.substitution(expr);
        return this;
    }

    @Override
    public ReturnType getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + getStringValue() + second.toString() + ")";
    }
}
