package expression;

public interface Expression {

    Expression substitution(Expression expression);

    ReturnType getReturnType();

    Simple getSimple();

}
