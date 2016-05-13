package me.chyc.entity;

/**
 * Created by yicun.chen on 9/27/14.
 */
public class Pair<T1, T2> extends Object {
    public T1 value1;
    public T2 value2;

    public Pair(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public int hashCode() {
        return this.value1.hashCode() * this.value2.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Pair))
            return false;

        Pair<T1, T2> p = (Pair<T1, T2>) obj;
        if (value1.equals(p.value1) && value2.equals(p.value2))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                '}';
    }

}
