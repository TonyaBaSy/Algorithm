package sorts;

import java.util.Arrays;

/**
 * Created by Tony on 2016/4/1.
 */
public class Merge {

    public static void merge(int[] a, int beg, int mid, int fin) {
        /**
         * 这三个指针是用来遍历数组b，因为构造的临时缓冲区b的下标由0开始，所有x=0
         */
        int x = 0;
        mid = mid - beg;
        int y = mid+1;
        /**
         * * * 自顶向下递归归并排序
         *
         * 为什么需要这个b，为什么不用一个空的b，从而不用复制，直接将结果保存到空的b中？
         * 使用空的b，两个指针遍历的是无序序列 a，因此无论怎么递归，都是对两个无序列a[beg...mid]和a[mid+1...fin]归并
         */
        int[] b = new int[fin - beg + 1];   //缓冲区的长度为 merge 的长度，不需要全部复制，仅仅复制了有效部分，这样节省了空间
        System.arraycopy(a, beg, b, 0, b.length);
        for (int i = beg/*i是遍历a的指针*/; i <= fin; i++) {
            if (x > mid) {
                System.arraycopy(b, y, a, i, fin - i + 1/*这里的是要复制到长度，也可以表示为：b.length-y*/);
                break;
            } else if (y > (fin-beg)) {
                System.arraycopy(b, x, a, i, mid + 1 - x/*这里也可以是 fin-i+1*/);
                break;
            } else if (b[x] < b[y]) {
                a[i] = b[x++];
            } else {
                a[i] = b[y++];
            }
        }
    }

    public static void anotherMerge(int[] a){
        // sz是半个区间的长度，绝对不是下标！
        for(int sz=1;sz<a.length;sz*=2) {
            for (int j = 0; j < a.length - sz/*控制j不越界，即控制与j相关的下标（mid）*/; j += sz+sz) {
                int mid = j + sz - 1;
                merge(a, j, mid, Math.min(j + sz + sz - 1, a.length - 1));
            }
        }
    }

    public static void sort(int a[], int beg, int fin) {
        if (beg >= fin) return;
        int mid = beg + (fin - beg) / 2;
        sort(a, beg, mid);
        sort(a, mid + 1, fin);
        merge(a, beg, mid, fin);
    }

    public static void main(String[] args) {
        int a[] = Tools.getRandomArray(50);
        anotherMerge(a);
        sort(a, 0, a.length - 1);
        for (int i=0;i<a.length;i++) {
            if (i>0)
                System.out.print(" ");
            System.out.print(a[i]);
        }
    }
}
