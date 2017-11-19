package sort;

import java.util.Stack;

/**
 * 堆排序
 * 使用最大堆
 * @author sky
 *
 */
public class MaxHeap  extends Sort{

	@Override
	public int[] sort(int[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据数据构造一颗完全二叉树
	 * @param data
	 */
	public void constructTree(int[] data) {
		Stack<Node> region=new Stack<>();
		Node nodeRoot=new Node(data[0],0, null, null);
		region.push(nodeRoot);
		
		int depth=0;//深度
		for(int i=1;i<data.length;i=i+2) {
			depth++;
			Node root=region.pop();
			Node nodeL=new Node(data[i],depth, null, null);
			root.setNodeL(nodeL);//左节点
			region.push(nodeL);
			Node nodeR=new Node(data[i+1],depth, null, null);
			root.setNodeR(nodeR);//右节点
			region.push(nodeR);
			if(depth==3) {
					Node ch=region.pop();
					while(ch.getDepth()>=3) {//找到上一级节点
						if(!region.empty()) {
							ch=region.pop();
						}
						else {//没有上一级节点
							break;
						}
					}
					depth=ch.getDepth();
					region.push(ch);
			}
			// 深度大于3，左边的树按线性增长
			
		}
	}
	
	
}


class Node{
	private int data=0;
	private Node nodeL=null;
	private Node nodeR=null;
	private int depth=0;
	
	public Node(int data,int depth, Node nodeL, Node nodeR) {
		super();
		this.data = data;
		this.nodeL = nodeL;
		this.nodeR = nodeR;
		this.depth=depth;
	}
	
	public int getDepth() {
		return depth;
	}
	
	/**
	 * @return the data
	 */
	public int getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(int data) {
		this.data = data;
	}
	/**
	 * @return the nodeL
	 */
	public Node getNodeL() {
		return nodeL;
	}
	/**
	 * @param nodeL the nodeL to set
	 */
	public void setNodeL(Node nodeL) {
		this.nodeL = nodeL;
	}
	/**
	 * @return the nodeR
	 */
	public Node getNodeR() {
		return nodeR;
	}
	/**
	 * @param nodeR the nodeR to set
	 */
	public void setNodeR(Node nodeR) {
		this.nodeR = nodeR;
	}
	
	
}