package expression;

import base.Triple;

import java.util.Arrays;

public class Equal extends AbstractBinaryExpression {
    public Equal(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.INT, ReturnType.INT));
    }

    @Override
    protected String getStringValue() {
        return "=";
    }

    @Override
    public ReturnType getReturnType() {
        return ReturnType.BOOL;
    }

    @Override
    public Simple getSimple() {
        Polynomial fp = (Polynomial) first.getSimple();
        Polynomial sp = (Polynomial) second.getSimple();
        Polynomial r = fp.sub(sp);
        if (r.isZero()) {
            return BooleanValues.TRUE;
        }
        BooleanValues ans = BooleanValues.UNKNOWN;
        ans.setExpression(new Equal(r.getExpression(), new Const(0)));
        return ans;
    }
}
