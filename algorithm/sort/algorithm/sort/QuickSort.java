package sort;

import java.util.Arrays;
import java.util.Stack;

/**
 * 快速排序算法实现，
 * 按升序排序
 * @author sky
 *
 */
public class QuickSort extends Sort{

	public static void main(String[] args) {
		QuickSort sort=new QuickSort();
		
		//测试数据
		int[] data= {5,100,10,9,8,200,4,70}; 
//		int[] data= {1,1,1,1,1};
//		int [] data= {7,6,5,4,3,2,1};
//		int [] data= {1,2,3,4,5,6};
//		int [] data= {1, 6, 5, 4, 3, 2, 7};
//		int [] data= {1};
//		int[] data2= {20, 8};
//		int position = sort.partition(data2,0,4);
//		
//		System.out.println(position);
//		System.out.println(Arrays.toString(data2));
//		System.exit(0);
//		position = sort.partition(data,0,3);
//		System.out.println(position);
//		System.out.println(Arrays.toString(data));
		
//		sort.sort(data);
		System.out.println(" "+Arrays.toString(data));
//		sort.sortQuick(data);
		sort.sort(data);
		System.out.println(Arrays.toString(data));
		
		
	}
	
	
	
//	@Override
	public int[] sort(int[] data,int left,int right){
		int middle=0;
		if(left<right) {
			middle=partition(data, left, right);
			
//			System.out.println("4from "+left+" to "+(middle-1)+"  "+Arrays.toString(data));
			sort(data,left,middle-1);
			sort(data,middle+1,right);
//			System.out.println("4from "+left+" to "+(middle+1)+"  "+Arrays.toString(data));
		}
		
		return null;
	}
	
	private void swap(int[] data,int k1,int k2) {
		int tmp=data[k1];
		data[k1]=data[k2];
		data[k2]=tmp;
	}
	
	/**
	 * 一趟快速排序，将给定区域的数据做一次快排，返回分区的中间位置下标
	 * @param data
	 * @param leftPoint 需要快排的起点 下标 最小从0开始
	 * @param rightPoint 需要快排的终点 下标
	 * @return
	 */
	public int  partition(int [] data,int leftPoint,int rightPoint) {
	
		int head=data[leftPoint];
		int i=leftPoint+1,j=rightPoint;
		
		while(i<j) {
			while(data[i]<=head&&i<rightPoint)//边界溢出处理
				i++;
			while(data[j]>=head&&j>leftPoint+1)
				j--;
			swap(data, i, j);
		}
		swap(data, i, j);
		if(data[leftPoint]>data[j]) {
			swap(data, leftPoint, j);
			return j;
		}
		return leftPoint;
	}



	/**
	 * 快速排序递归实现
	 */
	public int[] sort(int[] data) {
		
		// TODO Auto-generated method stub
		int start=0;
		int end=data.length-1;
		sort(data, start, end);
		return data;
	}

	
	/**
	 * 快速排序，迭代实现
	 */
	public void sortQuick(int [] data) {
		int left=0;
		int right=data.length-1;
		Stack<Pointer> regoin=new Stack<>();
		regoin.push(new Pointer(left, right));
		
		while(!regoin.empty()) {
			Pointer pointer=regoin.pop();
			left=pointer.getLeft();
			right=pointer.getRight();
			int middle=0;
			if(left<right) {
				middle=partition(data, left, right);
				if(left<middle-1)
					regoin.push(new Pointer(left, middle-1));
				if(middle+1<right)
					regoin.push(new Pointer(middle+1, right));
			}
		}
	}
}


/**
 * 保存两个位置变量
 * @author sky
 *
 */
class Pointer{
	int left;
	int right;
	public Pointer(int left,int right) {
		this.left=left;
		this.right=right;
	}
	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}
	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}
	
	
}
