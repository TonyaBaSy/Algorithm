package sorts;

/**
 * Created by Tony on 2016/4/6.
 */
public class Heap{
    public void swim(int[] a, int n){

    }

    public static void sink(int[] a, int n, int end){
        int j = n*2;
        while(j <= end) {
            if(j<=(end-1) && a[j]<a[j+1]) j++;
            if(a[n]>=a[j]) break;
            Tools.swap(a, n, j);
            n = j;
            j = n*2;
        }
    }

    public static void sort(int[] a, int beg, int end){
        int N = end;
        for(int i=N/2; i>=beg; i--)
            sink(a, i, N);
//        printArray(a);
        int i=N;
        while(i>0){
            Tools.swap(a, 1, i--);
            sink(a, 1, i);
        }
    }

    public static void printArray(int[] a){
        for(int i=1; i<a.length; i++){
            if (i>1)
                System.out.printf(" %d", a[i]);
            else
                System.out.printf("%d", a[i]);
        }
    }
    public static void main(String[] args){
        int[] a = Tools.getRandomArray(100);
        sort(a, 1, a.length-1);
        printArray(a);
    }
}
