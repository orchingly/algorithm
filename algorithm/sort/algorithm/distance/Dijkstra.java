package distance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Dijkstra {

	private Graph graph;
	/**已找到最短路径顶点，权重集合*/
	private List<VertextSort> vertextSorts;
	
	
	public void initDijkstra(Graph graph) {
		this.graph=graph;
	}
	
	private void trackPrint() {
		String v0="v0";
		for(VertextSort vertextSort:vertextSorts) {
			Stack<VertextSort> stack=new Stack<>();
			stack.push(vertextSort);
			while(vertextSort.getPreVertex()!=null) {
				vertextSort=vertextSort.getPreVertex();
				stack.push(vertextSort);
			}
			System.out.print(v0);
			while(!stack.isEmpty()) {
				VertextSort vt=stack.pop();
				System.out.print(" -> ("+vt.getV()+" : "+vt.getW()+")");
			}
			System.out.println();
		}
		
	}
	
	
	public void findMin(String vertext) {
		//已经找到的
		vertextSorts=new ArrayList<>();
		Set<String> vertextFound=new HashSet<>();
		Set<String> vertextNotFound=new HashSet<>();
		for(String v:graph.getVertex())
			vertextNotFound.add(v);
		//初始节点
		VertextSort vertextSortRoot=new VertextSort(vertext, 0, null);
		vertextSortRoot.setNextMinWeightVertex(null);
		vertextSorts.add(vertextSortRoot);
		vertextNotFound.remove(vertext);
		vertextFound.add(vertext);
		while(true) {
			if(vertextSorts.size()==graph.getNumVertex())
		 		break;
			Iterator<VertextSort> ite=vertextSorts.iterator();
			List<VertextSort> vertextList=new ArrayList<>();
			//对每一个以求得的顶点寻找他的下一个顶点（最小权重）
			while(ite.hasNext()) {
				VertextSort vtSort=ite.next();
				String[] vtNames=graph.getNextVertexts(vtSort.getV());
				double minWeight=Double.POSITIVE_INFINITY;
				String minVertextName=null;
				for(String vtName:vtNames){
					if(vertextFound.contains(vtName))
						continue;
					double weight=graph.getWeight(vtSort.getV(), vtName);
					if(minWeight>weight) {
						minWeight=weight;
						minVertextName=vtName;
					}
				}
				if(minVertextName!=null) {//找到最小权重的一个邻接点
					VertextSort nextMinWeightVertex=new VertextSort(minVertextName, minWeight+vtSort.getW(), vtSort);
					vertextList.add(nextMinWeightVertex);
					vtSort.setNextMinWeightVertex(nextMinWeightVertex);
				}
			}
			Collections.sort(vertextList);//最后一个v1未被加入，vertextListsize = 0
			if(vertextList.size()==0)//剩下的点无法通过跳点到达
				break;
			vertextSorts.add(vertextList.get(0));
			vertextFound.add(vertextList.get(0).getV());
			vertextNotFound.remove(vertextList.get(0).getV());
		}
		for(String nv:vertextNotFound) {
			vertextSorts.add(new VertextSort(nv, Double.POSITIVE_INFINITY, vertextSortRoot));
		}
	}
	
	
	/**
	 * 经过指定路径到达某一个点的距离
	 * @param v0 始点
	 * @param vsList 经过顺序，最后一个为终点
	 * @return
	 */
	public double addWeight(String v0,List<String> vsList,String target) {
		double weight=0;
		List<String> newList=new ArrayList<>(vsList);
		newList.add(target);
		for(String v:newList) {
			double tw=graph.getWeight(v0, v);
			v0=v;
			weight+=tw;
			if(weight==Double.POSITIVE_INFINITY)//不联通
				break;
		}
		return weight;
	}
	
	
	public static void main(String[] args) {
//		String[] vertex= {"v0","v1","v2","v3","v4","v5"};
//		Graph graph=new Graph(vertex);
//		graph.addEdge(10, "v0", "v2");
//		graph.addEdge(30, "v0", "v4");
//		graph.addEdge(100, "v0", "v5");
//		graph.addEdge(5, "v1", "v2");		
//		graph.addEdge(50, "v2", "v3");
//		graph.addEdge(10, "v3", "v5");
//		graph.addEdge(20, "v4", "v3");
//		graph.addEdge(60, "v4", "v5");
		
		String[] vertex= {"A","B","C","D","E","F","G","H","I"};
		Graph graph=new Graph(vertex);
		graph.addEdge(10, "A", "B");
		graph.addEdge(11, "A", "F");
		graph.addEdge(18, "B", "C");
		graph.addEdge(12, "B", "I");		
		graph.addEdge(8, "C", "I");
		graph.addEdge(16, "B", "G");
		graph.addEdge(17, "F", "G");
		graph.addEdge(19, "G", "H");
		graph.addEdge(24, "G", "D");
		graph.addEdge(22, "C", "D");
		graph.addEdge(21, "I", "D");
		graph.addEdge(20, "E", "D");
		graph.addEdge(16, "H", "D");
		graph.addEdge(7, "E", "H");
		graph.addEdge(29, "F", "E");
		
		
		System.out.println(graph);
		
		
		
		Dijkstra dij=new Dijkstra();
		dij.initDijkstra(graph);
		
		dij.findMin("A");
		dij.trackPrint();
	}
}

/**
 * 迭代过程需要不断更新的顶点
 * @author sky
 *
 */
class VertextSort implements Comparable<VertextSort>{
	/**当前状态起始节点到当前节点的最短距离/权重 */
	private double minWeight=0;
	/**当前结点的父节点*/
	private VertextSort preVertex;
	/**字符串名称*/
	private String  vertextName;
	/**下一个和自己有最小权重的顶点*/
	private VertextSort nextMinWeightVertex=null;

	/**
	 * @param nextMinWeightVertex the nextMinWeightVertex to set
	 */
	public void setNextMinWeightVertex(VertextSort nextMinWeightVertex) {
		this.nextMinWeightVertex = nextMinWeightVertex;
	}
	
	/**获取下一个和自己有最小权重的顶点*/
	public VertextSort getNextMinWeightVertex() {
		return this.nextMinWeightVertex;
	}
	public double getW() {
		return minWeight;
	}
	public String getV() {
		return vertextName;
	}
	
//	public List<String > getTrack(){
//		return track;
//	}
	
	public VertextSort getPreVertex() {
		return this.preVertex;
	}
	
	public VertextSort(String v,double w) {
		this.vertextName=v;
		this.minWeight=w;
	}
	
	public VertextSort(String v,double w,VertextSort preVertex) {
		this.vertextName=v;
		this.minWeight=w;
		this.preVertex=preVertex;
	}
	
//	public VertextSort(String v,double w/*,List<String> track*/) {
//		this.vertextName=v;
//		this.weight=w;
////		this.track=new ArrayList<>(track);
//	}
	
	@Override
	/**
	 * 按升序排序
	 */
	public int compareTo(VertextSort o) {
		if(o.getW()>minWeight)
			return -1;
		else {
			return 1;
		}
	}
	
	public String toString() {
		return "name:"+vertextName+" track : "+minWeight+"";
	}
	
}