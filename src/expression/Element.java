package expression;

public class Element implements Expression {
    @Override
    public Expression substitution(Expression expression) {
        return expression;
    }

    @Override
    public ReturnType getReturnType() {
        return ReturnType.INT;
    }

    @Override
    public String toString() {
        return "element";
    }
}
