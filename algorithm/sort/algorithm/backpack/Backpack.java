package backpack;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态规划解决0-1背包问题
 * @author sky
 *
 */
public class Backpack {
	
	public static void main(String[] args) {
		Backpack backpack=new Backpack();
		backpack.back();
	}

	/**
	 * 背包问题
	 */
	public void back() {
		int x=0,y=0;
		int totalWeight=8;
		int thingsNum=4;
		Thing[] things=new Thing[thingsNum];
		things[3]=new Thing(2, 3);
		things[0]=new Thing(3, 4);
		things[1]=new Thing(4, 5);
		things[2]=new Thing(5, 6);
		int [][] cut_value=new int[thingsNum+1][totalWeight+1];
		int maxValue=0;
		for(int i=1;i<cut_value.length;i++) {
			for(int j=1;j<cut_value[i].length;j++) {//当前背包容量
				int temp=0;
				if(j>=things[i-1].getWeight()) {//装得下
					temp = maxValue(i,j,cut_value, things[i-1].getWeight(),things[i-1].getValue());
					cut_value[i][j]=temp;
				}
				else {
					temp=cut_value[i][j]=cut_value[i-1][j];
				}
				if(maxValue<temp) {
					maxValue=temp;
					x=i;
					y=j;
				}
					
			}
		}
		
		System.out.println(maxValue);
		for(int i=0;i<cut_value.length;i++) {
			for(int j=0;j<cut_value[i].length;j++) {
				System.out.print(cut_value[i][j]+"  ");
			}
			System.out.println();
		}
		
		List<Integer> position = rollback(cut_value, things, x, y);
		System.out.println(position);
	}
	
	/**
	 * 求解最大值
	 * @param i
	 * @param copacity
	 * @param packBefore
	 * @param cut_weight
	 * @param cut_value
	 * @return
	 */
	public int maxValue(int i,int copacity,int[][] packBefore,int cut_weight,int cut_value) {
		int max=0;
		max=packBefore[i-1][copacity];
		if(max>packBefore[i-1][copacity-cut_weight]+cut_value)
			return max;
		else {
			return packBefore[i-1][copacity-cut_weight]+cut_value;
		}
	}
	
	/**
	 * 回溯法寻找最佳组合的点
	 * @param rolldata
	 * @param wv
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Integer> rollback(int [][] rolldata,Thing[] wv, int x,int y) {
		int i=x; 
		int j=y;
		List<Integer> xValue=new ArrayList<>();
		while(i>0) {
//			System.out.println("i = "+i+", j = "+j);
			
			if(rolldata[i][j]==rolldata[i-1][j]) {
				//没有装入当前物品
			}
			else if(rolldata[i][j]!=rolldata[i-1][j]&&rolldata[i][j]==rolldata[i-1][j-wv[i-1].getWeight()]+wv[i-1].getValue()){
				xValue.add(i-1);//记录当前物品编号
				j=j-wv[i-1].getWeight();
			}
			i--;
		}
		return xValue;
	}
}


class Thing{
	public Thing(int weight,int value) {
		// TODO Auto-generated constructor stub
		this.weight=weight;
		this.value=value;
	}
	
	private int weight;
	private int value;
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	
}