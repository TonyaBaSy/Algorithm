package sorts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Tony on 2016/3/28.
 */
public class Tools {

    public static void swap(int[] a, int i1, int i2) {
        int temp = a[i1];
        a[i1] = a[i2];
        a[i2] = temp;
    }

    public static int[] getRandomArray(int len) {
        int[] a = new int[len];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (a.length * 10 * Math.random());
        }
        return a;
    }

    public static void print(int[] a){
        int i = 0;
        while(i<a.length){
            if (i>0)
                System.out.printf(" %d", a[i++]);
            else
                System.out.printf("%d", a[i++]);
        }
    }

    public static void useTime(int[] a, Class cl) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = cl.getMethod("sort", int[].class, int.class, int.class);
        String name = cl.getName();
        int times=0;
        long time=0;
        while(times<1000) {
            long begT = System.currentTimeMillis();
            m.invoke(null, a, 0, a.length-1);
            times++;
            time += System.currentTimeMillis() - begT;
        }
        System.out.println(name + " use time is: " + time + "ms");
    }
}
