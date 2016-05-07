package sorts;

import java.util.Arrays;

/**
 * Created by Tony on 2016/4/1.
 */
public class Merge {

    public static void merge(int[] a, int beg, int mid, int fin) {
        /**
         * ������ָ����������������b����Ϊ�������ʱ������b���±���0��ʼ������x=0
         */
        int x = 0;
        mid = mid - beg;
        int y = mid+1;
        /**
         * * * �Զ����µݹ�鲢����
         *
         * Ϊʲô��Ҫ���b��Ϊʲô����һ���յ�b���Ӷ����ø��ƣ�ֱ�ӽ�������浽�յ�b�У�
         * ʹ�ÿյ�b������ָ����������������� a�����������ô�ݹ飬���Ƕ�����������a[beg...mid]��a[mid+1...fin]�鲢
         */
        int[] b = new int[fin - beg + 1];   //�������ĳ���Ϊ merge �ĳ��ȣ�����Ҫȫ�����ƣ�������������Ч���֣�������ʡ�˿ռ�
        System.arraycopy(a, beg, b, 0, b.length);
        for (int i = beg/*i�Ǳ���a��ָ��*/; i <= fin; i++) {
            if (x > mid) {
                System.arraycopy(b, y, a, i, fin - i + 1/*�������Ҫ���Ƶ����ȣ�Ҳ���Ա�ʾΪ��b.length-y*/);
                break;
            } else if (y > (fin-beg)) {
                System.arraycopy(b, x, a, i, mid + 1 - x/*����Ҳ������ fin-i+1*/);
                break;
            } else if (b[x] < b[y]) {
                a[i] = b[x++];
            } else {
                a[i] = b[y++];
            }
        }
    }

    public static void anotherMerge(int[] a){
        // sz�ǰ������ĳ��ȣ����Բ����±꣡
        for(int sz=1;sz<a.length;sz*=2) {
            for (int j = 0; j < a.length - sz/*����j��Խ�磬��������j��ص��±꣨mid��*/; j += sz+sz) {
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
