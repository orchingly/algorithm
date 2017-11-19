package distance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Graph {
	
	private double[][] edge;
	private int numVertex=0;
	private int numEdge=0;
	private String[] vertex;
	/**顶点名称与坐标对应关系*/
	private Map<String,Integer> vertext_order;
	/**坐标与顶点的关系*/
	private Map<Integer,String> order_vertex;

	public String[] getVertex() {
		return vertex;
	}
	
	
	/**
	 * @return the numVertex
	 */
	public int getNumVertex() {
		return numVertex;
	}

	/**
	 * @return the numEdge
	 */
	public int getNumEdge() {
		return numEdge;
	}

	
	/**
	 * 初始化顶点
	 * @param numVertex
	 * @param vertext
	 */
	public Graph(String [] vertext) {
		this.numVertex=vertext.length;
		edge=new double[numVertex][numVertex];
		vertex=vertext;
		this.vertext_order=new HashMap<>();
		this.order_vertex=new HashMap<>();
		int k=0;
		for(String vt:vertext) {
			vertext_order.put(vt, k);
			order_vertex.put(k++,vt);
		}
		//初始化开边权重无穷大
		for(int i=0;i<numVertex;i++) {
			for(int j=0;j<numVertex;j++) {
				if(i==j)
					edge[i][j]=0;
				else
					edge[i][j]= Double.POSITIVE_INFINITY;
			}
		}
	}
	
	/**
	 * 添加一条边，从vertex1到vertex2的边,如果顶点未定义，添加失败，返回false
	 * @param weight 权重
	 * @param vertex1 顶点
	 * @param vertext2 顶点
	 * 
	 */
	public boolean addEdge(double weight,String vertex1,String vertext2) {
		if(vertext_order.containsKey(vertex1)&&vertext_order.containsKey(vertext2)) {
			int x=vertext_order.get(vertex1);
			int y=vertext_order.get(vertext2);
			addEdge(weight, x, y);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**添加一条有向边*/
	public void addEdge(double weight,int x,int y) {
		edge[x][y]=weight;
		numEdge++;
	}
	
	/**
	 * 返回点x->点y的权重
	 * @param x 从0开始
	 * @param y 从0开始
	 * @return 权重
	 */
	public double getWeight(int x,int y) {
		return edge[x][y];
	}
	
	public double getWeight(String v1,String v2) {
		if(vertext_order.containsKey(v1)&&vertext_order.containsKey(v2))
			return getWeight(vertext_order.get(v1), vertext_order.get(v2));
		else {
			throw new VertextNotExistsException(v1+" , "+v2 +" 不存在");
		}
	}
	
	/**
	 * 获取顶点vertex能够直接到达的顶点下标，即边的权重不为INFITY，除去本身
	 * @param vertex 顶点下标，从0开始
	 * @return 邻接顶点数组
	 */
	public int[] getNextVertex(int vertex) {
		List<Integer> v_ids=new ArrayList<>();
		for(int i=0;i<edge.length;i++) {
			if(edge[vertex][i]!=Double.POSITIVE_INFINITY&&edge[vertex][i]!=0) {
				v_ids.add(i);
			}
		}
		int i=0;
		int[] vertexs=new int[v_ids.size()];
		for(int v:v_ids)
			vertexs[i++]=v;
		return vertexs;
	}
	
	/**
	 * 根据顶点名称返回可以直接到达的顶点
	 * @param vertexName 名称
	 * @return int[]
	 * @see #getNextVertex(int)
	 */
	public int[] getNextVertex(String vertexName) {
		int vertext=vertext_order.get(vertexName);
		return getNextVertex(vertext);
	}
	
	public String[] getNextVertexts(String vName) {
		int vertext=vertext_order.get(vName);
		int[] v_ids=getNextVertex(vertext);
		String [] verts=new String[v_ids.length];
		int v=0;
		for(int v_id:v_ids)
			verts[v++]=order_vertex.get(v_id);
		return verts;
	}
	
	public List<String> DFS(Graph graph,String startV) {
		Stack<String> statck=new Stack<>();
		Set<String> vertextNotVisit=new HashSet<>();
		List<String> visit=new ArrayList<>();
		for(String t : graph.getVertex())
			vertextNotVisit.add(t);
		statck.push(startV);
		while(true) {
			while(!statck.isEmpty()) {
				
				String vertextName=statck.pop();
				if(vertextNotVisit.contains(vertextName)) {//未被访问
					visit.add(vertextName);
					vertextNotVisit.remove(vertextName);//删除
				}
				String[] nextVertexts=graph.getNextVertexts(vertextName);
				for(String v:nextVertexts) {
					if(vertextNotVisit.contains(v))//只入栈未被访问的节点
						statck.push(v);
				}
			}
			if(vertextNotVisit.isEmpty())
				break;
			if(visit.size()!=graph.getNumVertex()) {//处理不能通过起始节点通过某条路径直接到达的顶点
				for(String notVisit:vertextNotVisit) {
					statck.push(notVisit);
//					vertextNotVisit.remove(notVisit);
					break;
				}
			}
			
		}
		return visit;
	}
	
	@Override
	public String toString() {
		String string="\t";
		for(String v:vertex){
			string+=v+"  ";
		}
		string+="\n";
		String row="";
		for(int i=0;i<edge.length;i++) {
			row+=vertex[i]+"  ";
			for(int j=0;j<edge.length;j++)
				row=row+edge[i][j]+"  ";
			row+="\n";
		}
		
		return string+row;
	}
	
	public static void main(String[] args) {
		System.out.println();
		String[] vertex= {"v0","v1","v2","v3","v4","v5"};
		Graph graph=new Graph(vertex);
		graph.addEdge(10, "v0", "v2");
		graph.addEdge(30, "v0", "v4");
		graph.addEdge(100, "v0", "v5");
		graph.addEdge(5, "v1", "v2");		
		graph.addEdge(50, "v2", "v3");
		graph.addEdge(10, "v3", "v5");
		graph.addEdge(20, "v4", "v3");
		graph.addEdge(60, "v4", "v5");
		
		
		System.out.println(graph);
		List<String> order=graph.DFS(graph, "v0");
		System.out.println(order);
		
		
	}
}


class VertextNotExistsException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msString="Not found";
	
	public VertextNotExistsException(String msg) {
		// TODO Auto-generated constructor stub
		this.msString=msg;
	}
	public String getMessage() {
		return msString;
	}
	
}