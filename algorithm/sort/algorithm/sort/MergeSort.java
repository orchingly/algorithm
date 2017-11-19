package sort;

import java.util.Arrays;

public class MergeSort extends Sort{

	public static void main(String[] args) {
		MergeSort sort=new MergeSort();
		
		//data
		System.out.println("归并排序：");
		int[] data=new int[5];
		data[0]=1;
		data[1]=9;
		data[2]=3;
		data[3]=7;
		data[4]=0;
		sort.sort(data);
		System.out.println(Arrays.toString(data));
	}
	
	@Override
	/**
	 * 归并排序
	 * @param data
	 * @param mergeResult 排序结果
	 */
	public int[] sort(int[] data) {
		int length=data.length;
		if(length>1) {
			int halfLength=length/2;//向下取整
			int [] first=copyTo(data, 0, halfLength);
			int [] second=copyTo(data, halfLength, length);
			
			sort(first);
			sort(second);
			
			merge(first, second,data);
		}
		return null;
	}
	
	
	private int[] copyTo(int[] data,int start,int end) {
		int[] copy=new int[end-start];
		for(int i=start,j=0;i<end;j++,i++) {
			copy[j]=data[i];
		}
		return copy;
	}
		
	/**
	 * 合并两个有序数组,递增
	 * @param first
	 * @param second
	 */
	private void merge(int [] first,int[] second,int[] merge) {
		int position=0;
		int j=0;
		int i=0;
		System.out.println("first  : "+Arrays.toString(first)+" second : "+Arrays.toString(second));
		while(true) {
			if(j<second.length&&i<first.length) {
				if(first[i]<second[j]) {
					merge[position++]=first[i++];
				}
				else {
					merge[position++]=second[j++];
				}
			}
			else {
				break;
			}
		}
		if(j<second.length) {
			while(j<second.length)
				merge[position++]=second[j++];
		}
		if (i<first.length) {
			while(i<first.length)
				merge[position++]=first[i++];
		}
		
		System.out.println("merge : "+Arrays.toString(merge));
	}
}

