import sorts.Merge;
import sorts.Quick;
import sorts.Tools;

/**
 * Created by Tony on 2016/4/1.
 */
class quick{
	/**
	 * 这种有中间值，一趟排序后这个中心点将落入正确位置
	 * @param a
	 * @param beg
	 * @param end
	 * @return
	 */
	private static int partition(int[] a, int beg, int end){
		int x = beg;
		int y = end + 1;
//		int mid = beg+(end-beg)/2;
		int pivot = a[beg];	//中心点

		while(true){
			while(a[++x]<pivot) if(x == end) break;	//begin in index 1
			while(pivot<a[--y]) if(y == beg) break;	//begin in index a.length-1
			if(x>=y) break;
			Tools.swap(a, x, y);
		}
		Tools.swap(a, beg, y);	//将中心点放入正确的位置，这步很精妙
		return y;
	}

	public static void sort(int[] a, int x, int y){
		if((y-x) < 1) return;
		int mid = partition(a, x, y);
		sort(a, x, mid-1);
		sort(a, mid+1, y);
	}
}
class quickII{
	/**
	 * 这个没有 中心点，但是也可以完成排序，它的原则是：一趟排序后，比中心点大的落入一侧，比中心点小的落入另一侧，中心点落入其中的一侧。
	 * @param a
	 * @param beg
	 * @param end
	 */
	private static void partition(int[] a, int beg, int end){
		int x = beg;
		int y = end;
		int pivot = a[beg+(end-beg)/2];

		while(x<=y){
			while(a[x]<pivot) x++;
			while(a[y]>pivot) y--;
			if(x<=y)
				Tools.swap(a, x++, y--);
		}
		if(x<end)	partition(a, x, end);
		if(y>beg)	partition(a, beg, y);
	}
	public static void sort(int[] a,int beg, int end){
		partition(a, beg, end);
	}
}

class shell{
	public static void sort(int[] a, int beg, int end){
		int h = beg;
		int length = end - beg + 1;
		while(h < length / 3) h = h * 3 + 1/*数学问题，这里的1变为2也是成立的*/;

		while(h > 0){
			for(int i=h; i< length;i++/*不是i+=h，因为虽然步长不是1了，但是还是要一组一组的排序*/) {
				for (int j = i; j >= h && a[j] < a[j - h]; j -= h) {
					Tools.swap(a, j, j - h);
				}
			}
			h /= 3;
		}
	}
}

class insert{
	public static void sort(int[] a,int beg, int end){
		for(int i=1; i<a.length; i++){
			for(int j=i; j>=1 && a[j]<a[j-1]; j--){
				Tools.swap(a, j, j - 1);
			}
		}
	}
}

class heap{
	private static void sink(int[] a, int beg, int end){
		while(beg*2<=end) {
			int j = beg * 2;
			if (j<end && a[j]<a[j+1]/*这里要控制j+1不越界*/) j++;
			if (a[beg] > a[j]) break;
			Tools.swap(a, beg, j);
			beg = j;
		}
	}

	public static void sort(int[] a, int beg, int end){
		a[0] = -1;
		int i = (a.length-1) / 2;
		while(i>0) sink(a, i--, a.length-1);
		i = a.length - 1;
//		Tools.swap(a, 1, a.length-1);
		while(i>1){
			Tools.swap(a, 1, i);
			sink(a, 1, --i);

		}
	}
}

class merge{
	private static void merge(int[] a, int beg, int mid, int end){
		int[] b = new int[end-beg+1];
		System.arraycopy(a, beg, b, 0, b.length);
		int x = 0;
		mid = mid-beg;
		int y = mid + 1;
		for(int i = beg; i<=end;i++/*这里已经有了递增的变量了*/){
			if(x>mid)			a[i]=b[y++];
			else if(y>end-beg)	a[i]=b[x++];
			else if(b[x]<b[y])	a[i]=b[x++];
			else				a[i]=b[y++];
		}
	}

	public static void sort(int[] a, int beg, int end){
		if(end-beg<1) return;
		int mid = beg + (end - beg) / 2;
		sort(a, beg, mid);
		sort(a, mid+1, end);
		merge(a, beg, mid, end);
	}

	public static void sortII(int[] a, int beg, int end){
		int len = end - beg + 1;
		for(int sz=1; sz<len; sz *= 2){
			for(int j=0; j+sz<=end; j+=sz+sz){
				merge(a, j, j+sz-1, Math.min(j+sz+sz-1, end));
			}
		}
	}
}
public class Test {
	public static void main(String[] args) {
		int[] a = Tools.getRandomArray(50);
		heap.sort(a, 0, a.length - 1);
		Tools.print(a);
	}
}
