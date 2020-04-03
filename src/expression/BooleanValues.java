package expression;

public enum BooleanValues implements Simple {
    TRUE, FALSE, UNKNOWN;

    private Expression expr;

    public Expression getExpression() {
        if (this == BooleanValues.TRUE) {
            return new Equal(new Const(0), new Const(0));
        } else if (this == BooleanValues.FALSE) {
            return new Equal(new Const(0), new Const(1));
        }
        return expr;
    }

    public void setExpression(Expression expr) {
        this.expr = expr;
    }
}
