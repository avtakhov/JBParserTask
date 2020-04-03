package expression;

import java.util.Arrays;

public class Polynomial implements Simple {
    private int[] arr;

    public Polynomial(int[] arr) {
        this.arr = arr;
        removeZero();
    }

    private void removeZero() {
        int l = arr.length - 1;
        while (l >= 0 && arr[l] == 0) {
            l--;
        }
        arr = Arrays.copyOf(arr, l + 1);
    }

    public boolean isZero() {
        return arr.length == 0;
    }

    public Polynomial add(Polynomial other) {
        int[] res = Arrays.copyOf(arr, Math.max(arr.length, other.arr.length));
        for (int i = 0; i < other.arr.length; i++) {
            res[i] += other.arr[i];
        }
        return new Polynomial(res);
    }

    public int[] getArr() {
        return arr;
    }

    public int degree() {
        return arr.length - 1;
    }

    public Polynomial sub(Polynomial other) {
        int[] res = Arrays.copyOf(arr, Math.max(arr.length, other.arr.length));
        for (int i = 0; i < other.arr.length; i++) {
            res[i] -= other.arr[i];
        }
        return new Polynomial(res);
    }

    public Polynomial mul(Polynomial other) {
        int[] res = new int[arr.length + other.arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < other.arr.length; j++) {
                res[i + j] += arr[i] * other.arr[j];
            }
        }
        return new Polynomial(res);
    }

    private Expression getTerm(int k) {
        if (k == 0) {
            return new Const(arr[k]);
        }
        Expression expr = new Element();
        for (int i = 0; i < k - 1; i++) {
            expr = new Multiply(expr, new Element());
        }
        if (arr[k] != 1) {
            expr = new Multiply(new Const(arr[k]), expr);
        }
        return expr;
    }

    public Expression getExpression() {
        if (isZero()) {
            return new Const(0);
        }
        Expression res = null;
        for (int k = arr.length - 1; k >= 0; k--) {
            if (arr[k] == 0) {
                continue;
            }
            Expression expr = getTerm(k);
            res = res == null ? expr : new Add(expr, res);
        }
        return res;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != Polynomial.class) {
            return false;
        }
        Polynomial p = (Polynomial) other;
        return Arrays.equals(arr, p.arr);
    }
}
