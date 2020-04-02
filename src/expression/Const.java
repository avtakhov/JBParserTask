package expression;

public class Const implements Expression {

    int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Expression substitution(Expression expression) {
        return this;
    }

    @Override
    public ReturnType getReturnType() {
        return ReturnType.INT;
    }
}
