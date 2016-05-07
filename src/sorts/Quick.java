package sorts;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by Tony on 2016/4/5.
 * Quick��QuickII��һЩ
 */
public class Quick{
    private static final int M = 1; //�����鳤��


    public static int partition(int[] a, int beg, int end){
        int x = beg;
        int y = end + 1;
        int v = a[beg]; //int v = a[beg]ʱ���ٶȻ�������������pivot��ѡȡʮ�ֵ���Ҫ
        while(true){
            while(v>a[++x])	if(x==end) break;	//�ҵ��������ҵ�һ����value��ģ�ѭ����ֹ
            while(v<a[--y])	if(y==beg) break;	//�ҵ����������һ����valueС�ģ�ѭ����ֹ
            if(x>=y) break;						//С�����ң���������ʱѭ����ֹ
            Tools.swap(a, x, y);				//С�����󣬴�������ʱ������ѭ������
        }
        Tools.swap(a, beg, y);					//��value������ȷ��λ��
        return y;
    }
    public static void sort(int[] a, int beg, int end){
        if((end-beg) < M) {
//            EasySorts.insert(a, beg, end);    //�ݹ���ֹ������������ĳ���Ϊ M
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