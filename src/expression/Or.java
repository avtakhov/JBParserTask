package expression;

import base.Triple;

public class Or extends AbstractBinaryExpression {
    public Or(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.BOOL, ReturnType.BOOL));
    }

    @Override
    String getStringValue() {
        return "|";
    }

    @Override
    public Simple getSimple() {
        BooleanValues fp = (BooleanValues) first.getSimple();
        BooleanValues sp = (BooleanValues) second.getSimple();
        if (fp == BooleanValues.TRUE || sp == BooleanValues.TRUE) {
            return BooleanValues.TRUE;
        }
        if (fp == BooleanValues.FALSE && sp == BooleanValues.FALSE) {
            return BooleanValues.FALSE;
        }
        if (fp == BooleanValues.FALSE) {
            return sp;
        }
        if (sp == BooleanValues.FALSE) {
            return fp;
        }
        BooleanValues ans = BooleanValues.UNKNOWN;
        ans.setExpression(new Or(fp.getExpression(), sp.getExpression()));
        return ans;
    }
}
