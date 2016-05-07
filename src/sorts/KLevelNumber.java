package sorts;

/**
 * Created by Tony on 2016/4/8.
 */
public class KLevelNumber {
    static boolean isfind;
    static int num = -1;

    public static int partition(int[] a, int beg, int end){
        int x = beg;
        int y = end + 1;
        int v = a[x];
        while(true){
            while(v>a[++x])	if(x==end) break;	//找到从左向右第一个比value大的，循环终止
            while(v<a[--y])	if(y==beg) break;	//找到从右往左第一个比value小的，循环终止
            if(x>=y) break;						//小数在右，大数在左时循环终止
            Tools.swap(a, x, y);				//小数在左，大数在右时交换，循环继续
        }
        Tools.swap(a, beg, y);
        return y;
    }

    public static int finMin(int[] a) {
        int min = a[0];
        int i = 1;
        while(i<a.length) {
            if (min > a[i]) min = a[i++];
            else i++;
        }
        return min;
    }

    public static int fidMax(int[] a) {
        int max = a[0];
        int i=1;
        while (i<a.length){
            if (max < a[i]) max = a[i++];
            else i++;
        }
        return max;
    }

    /**
     * 使用快排的思想获得第K大的数
     * @param a
     * @param beg
     * @param end
     * @param k
     */
    public static void search(int[] a, int beg, int end, int k){
        if ((k-1)==beg) {
            num = finMin(a);
            return;
        }
        if ((k-1)==end) {
            num = fidMax(a);
            return;
        }
        if ((end-beg)<1)
            return;
        int p = partition(a, beg, end);
        if (p==(k-1)){
            num=a[p];
            return;
        }
        else if(p > (k-1)) search(a, beg, p - 1, k);
        else search(a, p + 1, end, k);
    }
    public static void main(String[] args) {
        int[] a = new int[]{72, 82, 10, 9, 12, 15, 7, 77, 36, 5};
        search(a, 0, a.length-1, 10);
        QuickII.sort(a, 0, a.length - 1);
        Tools.print(a);
        System.out.println("\n"+num);
    }
}
