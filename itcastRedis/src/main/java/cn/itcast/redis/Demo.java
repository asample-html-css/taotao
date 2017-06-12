package cn.itcast.redis;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class Demo {

    public static void main(String[] args) {

        TwoTuple twoTuple = new TwoTuple(new String("123"),new Integer(123));

        System.out.println(twoTuple.first);
        System.out.println(twoTuple.second);
        System.out.println(twoTuple.toString());
    }
}

