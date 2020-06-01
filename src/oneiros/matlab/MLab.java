package oneiros.matlab;

import java.util.ArrayList;
import java.util.Arrays;

public class MLab {

    private MLab() {
    }

    public static double[] abs(double[] v) {
        double[] abs = new double[v.length];
        for (int i = 0; i < abs.length; i++) {
            abs[i] = Math.abs(v[i]);
        }
        return abs;
    }

    public static double[] add(double[] v, double a) {
        for (int i = 0; i < v.length; i++) {
            v[i] += a;
        }
        return v;
    }

    public static double[] add(double[] v, double[] a) {
        for (int i = 0; i < v.length; i++) {
            v[i] += a[i];
        }
        return v;
    }

    public static double[] diff(double[] v) {
        double[] diff = new double[v.length - 1];
        for (int i = 0; i < diff.length; i++) {
            diff[i] = v[i + 1] - v[i];
        }
        return diff;
    }

    public static double[] div(double[] v, double d) {
        for (int i = 0; i < v.length; i++) {
            v[i] /= d;
        }
        return v;
    }

    public static double[] div(double[] v, double[] d) {
        for (int i = 0; i < v.length; i++) {
            v[i] /= d[i];
        }
        return v;
    }

    public static double[] find(double[] v) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < v.length; i++) {
            if (v[i] != 0) {
                list.add(i + 1);
            }
        }
        double[] find = new double[list.size()];
        for (int i = 0; i < find.length; i++) {
            find[i] = list.get(i);
        }
        return find;
    }

    public static double[] findPositives(double[] v) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < v.length; i++) {
            if (v[i] > 0) {
                list.add(i + 1);
            }
        }
        double[] find = new double[list.size()];
        for (int i = 0; i < find.length; i++) {
            find[i] = list.get(i);
        }
        return find;
    }

    public static double[] get(double[] v, int from, int to) {
        if (from < 1) {
            throw new IllegalArgumentException("Input parameter 'from' must be > 0");
        } else if (to > v.length) {
            throw new IllegalArgumentException("Input parameter 'to' must be < v.length");
        } else if (from > to) {
            throw new IllegalArgumentException("Input parameter 'from' must be != 'to'");
        }
        from--;
        return Arrays.copyOfRange(v, from, to);
    }

    public static double[] get(double[] v, double[] in) {
        double[] get = new double[in.length];
        for (int i = 0; i < get.length; i++) {
            double d = in[i] - 1;
            int index = (int) d;
            if (d != index || index < 0) {
                throw new IllegalArgumentException("Indices must either be real positive integers or logicals");
            }
            get[i] = v[index];
        }
        return get;
    }

    public static int length(double[] v) {
        return v.length;
    }

    public static double[] ones(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input parameter must be >= 0");
        }
        double[] sign = new double[n];
        for (int i = 0; i < sign.length; i++) {
            sign[i] = 1;
        }
        return sign;
    }

    public static double[] prod(double[] v, double p) {
        for (int i = 0; i < v.length; i++) {
            v[i] *= p;
        }
        return v;
    }

    public static double[] prod(double[] v, double[] p) {
        for (int i = 0; i < v.length; i++) {
            v[i] *= p[i];
        }
        return v;
    }

    public static double[] set(double[] v, double[] in, double[] s) {
        if (in.length != s.length) {
            throw new IllegalArgumentException("The number of elements in 'in' and 's' must be the same");
        }
        for (int i = 0; i < in.length; i++) {
            double d = in[i] - 1;
            int index = (int) d;
            if (d != index || index < 1) {
                throw new IllegalArgumentException("Indices must either be real positive integers or logicals");
            }
            v[index] = s[i];
        }
        return v;
    }

    public static double[] sign(double[] v) {
        double[] sign = new double[v.length];
        for (int i = 0; i < sign.length; i++) {
            sign[i] = Math.signum(v[i]);
        }
        return sign;
    }

    public static int size(double[] v) {
        return v.length;
    }

    public static double[] sub(double[] v, double a) {
        for (int i = 0; i < v.length; i++) {
            v[i] -= a;
        }
        return v;
    }

    public static double[] sub(double[] v, double[] a) {
        for (int i = 0; i < v.length; i++) {
            v[i] -= a[i];
        }
        return v;
    }

    public static double[] zeros(int n) {
        return new double[n];
    }
}
