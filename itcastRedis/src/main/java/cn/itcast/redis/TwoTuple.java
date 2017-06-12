package cn.itcast.redis;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public  class TwoTuple<A,B> {
    public final A first;
    public final B second;

    public TwoTuple(A a ,B b) {
        this.first = a;
        this.second = b;
    }


    @Override
    public String toString() {
        return "TwoTuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }



}
