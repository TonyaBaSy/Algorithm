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
            while(v>a[++x])	if(x==end) break;	//�ҵ��������ҵ�һ����value��ģ�ѭ����ֹ
            while(v<a[--y])	if(y==beg) break;	//�ҵ����������һ����valueС�ģ�ѭ����ֹ
            if(x>=y) break;						//С�����ң���������ʱѭ����ֹ
            Tools.swap(a, x, y);				//С�����󣬴�������ʱ������ѭ������
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
     * ʹ�ÿ��ŵ�˼���õ�K�����
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
