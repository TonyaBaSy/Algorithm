package sorts;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Tony on 2016/4/7.
 */

public class QuickII {
    public static void partition(int[] a, int beg, int end){
        int x = beg;
        int y = end;
        int pivot = a[beg +(end-beg)/2];
        while(x<=y){
            while(a[x]<pivot) x++;
            while(pivot<a[y]) y--;
            if (x<=y) Tools.swap(a, x++, y--);
        }
        if ((y-beg)>0) partition(a, beg, y);
        if ((end-x)>0) partition(a, x, end);
    }

    public static void sort(int[] a, int beg, int end){
        partition(a, beg, end);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int[] a = Tools.getRandomArray(10000);
        sort(a, 0, a.length - 1);
        Tools.useTime(a, QuickII.class);
    }
}
