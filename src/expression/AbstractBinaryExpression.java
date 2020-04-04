package expression;

import base.Triple;

import java.util.Objects;

public abstract class AbstractBinaryExpression implements Expression {

    protected Expression first;
    protected Expression second;

    protected AbstractBinaryExpression(Expression first, Expression second, Triple<ReturnType, ReturnType, ReturnType> expectType) {
        this.first = first;
        this.second = second;
        if (expectType.second() != first.getReturnType() || expectType.third() != second.getReturnType()) {
            throw new TypeException("TYPE ERROR");
        }
    }

    protected abstract String getStringValue();

    public abstract ReturnType getReturnType();

    @Override
    public Expression substitution(Expression expr) {
        first = first.substitution(expr);
        second = second.substitution(expr);
        return this;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + getStringValue() + second.toString() + ")";
    }

}
