package expression;

import base.Triple;

public class And extends AbstractBinaryExpression {
    public And(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.BOOL, ReturnType.BOOL));
    }

    @Override
    String getStringValue() {
        return "&";
    }

    @Override
    public Simple getSimple() {
        BooleanValues fp = (BooleanValues) first.getSimple();
        BooleanValues sp = (BooleanValues) second.getSimple();
        if (fp == BooleanValues.FALSE || sp == BooleanValues.FALSE) {
            return BooleanValues.FALSE;
        }
        if (fp == BooleanValues.TRUE && sp == BooleanValues.TRUE) {
            return BooleanValues.TRUE;
        }
        if (fp == BooleanValues.TRUE) {
            return sp;
        }
        if (sp == BooleanValues.TRUE) {
            return fp;
        }
        BooleanValues ans = BooleanValues.UNKNOWN;
        ans.setExpression(new And(fp.getExpression(), sp.getExpression()));
        return ans;
    }
}
