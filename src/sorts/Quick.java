package sorts;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by Tony on 2016/4/5.
 * Quick比QuickII快一些
 */
public class Quick{
    private static final int M = 1; //子数组长度


    public static int partition(int[] a, int beg, int end){
        int x = beg;
        int y = end + 1;
        int v = a[beg]; //int v = a[beg]时，速度会很慢很慢，因此pivot的选取十分的重要
        while(true){
            while(v>a[++x])	if(x==end) break;	//找到从左向右第一个比value大的，循环终止
            while(v<a[--y])	if(y==beg) break;	//找到从右往左第一个比value小的，循环终止
            if(x>=y) break;						//小数在右，大数在左时循环终止
            Tools.swap(a, x, y);				//小数在左，大数在右时交换，循环继续
        }
        Tools.swap(a, beg, y);					//将value放入正确的位置
        return y;
    }
    public static void sort(int[] a, int beg, int end){
        if((end-beg) < M) {
//            EasySorts.insert(a, beg, end);    //递归终止条件：子数组的长度为 M
            return;
        }
        int mid = partition(a, beg, end);
        sort(a, beg, mid-1);
        sort(a, mid+1, end);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int[] a = Tools.getRandomArray(10000);
        Tools.useTime(a, Quick.class);
        a=Tools.getRandomArray(10000);
        Tools.useTime(a, Heap.class);

    }
}