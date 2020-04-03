package expression;

import base.Triple;

import java.util.Arrays;

public class Equal extends AbstractBinaryExpression {
    public Equal(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return "=";
    }

    @Override
    public Simple getSimple() {
        Polynomial fp = (Polynomial) first.getSimple();
        Polynomial sp = (Polynomial) second.getSimple();
        if (fp.equals(sp)) {
            return BooleanValues.TRUE;
        }
        BooleanValues ans = BooleanValues.UNKNOWN;
        ans.setExpression(new Equal(fp.getExpression(), sp.getExpression()));
        return ans;
    }
}
