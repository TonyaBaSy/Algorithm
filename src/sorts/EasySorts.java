package sorts;

/**
 * Created by Tony on 2016/3/28.
 */
public class EasySorts {
    public static void shell(int[]a, int beg, int end) {

        int h = beg;
        int length = end - beg + 1;
        while (h < length/3)
            h = h * 3 + 1;

        while (h>=1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && a[j] < a[j - h]; j -= h) {
                    Tools.swap(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }
    public static void select(int[] a, int beg, int end){
        for (int i=beg;i<=end;i++){
            int min=i;
            for (int j=i;j<=end;j++) {
                if (!(a[min] < a[j])) min = j;
            }
            Tools.swap(a, min, i);
        }
    }
    public static void insert(int[] a, int beg, int end) {
        int h = beg+1;
        for (int i = h; i <= end; i++) {
            for (int j = i; j >= h && (a[j]<a[j - h]); j -= h) {
                Tools.swap(a, j, j - h);
            }
        }
    }
}
