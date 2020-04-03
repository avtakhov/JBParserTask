package expression;

import base.Triple;

public class Less extends AbstractBinaryExpression {
    public Less(Expression first, Expression second) {
        super(first, second, Triple.of(ReturnType.BOOL, ReturnType.INT, ReturnType.INT));
    }

    @Override
    String getStringValue() {
        return "<";
    }

    @Override
    public Simple getSimple() {
        Polynomial fp = (Polynomial) first.getSimple();
        Polynomial sp = (Polynomial) second.getSimple();
        Polynomial r = fp.sub(sp);
        if (r.degree() == 0) {
            return r.getArr()[0] < 0 ? BooleanValues.TRUE : BooleanValues.FALSE;
        }
        BooleanValues ans = BooleanValues.UNKNOWN;
        ans.setExpression(new Less(r.getExpression(), new Const(0)));
        return ans;
    }
}
