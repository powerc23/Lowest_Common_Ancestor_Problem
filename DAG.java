import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;

/* Name: Conor Power
 *  
 *  Various aspects of this code are based on Sedgewick and Wayne's 
 * implementation of a directed graph.
 * Instead of using a bag data structure, I have chosen to use an 
 * array list to represent the adjacency list for a vertex.
 */
public class DAG 
{
	private int E; //No. of edges
	private int V; //No. of vertices
	private int[] indegree;
	private int[] outdegree;
	private boolean[] visited;
	private boolean[] stack;
	private boolean hasCycle;
	private ArrayList<Integer>[] adj;

	public DAG(int V)
	{
		if(V < 0) throw new IllegalArgumentException("No. of vertices in a Digraph must be nonnegative");

		this.E = 0; //Initialize no. of edges to zero
		this.V = V; // Initialize no. of vertices
		visited = new boolean[V];
		indegree = new int[V];
		stack = new boolean[V];
		adj = new ArrayList[V];

		//Intialize adjacency lists for each vertex
		for(int i = 0; i < V; i++)
			adj[i] = new ArrayList<Integer>();

	}

	//Returns number of vertices in digraph
	public int V() 
	{
		return V;
	}

	//Returns number of edges in digraph
	public int E() {
		return E;
	}

	public boolean isEmpty()
	{
		if(V == 0) return true;
		else return false;
	}

	//Validates vertex v
	public int validateVertex(int v)
	{
		if(v < 0 || v >= V) return -1;
		else return 1;
	}

	public int indegree(int v)
	{
		if(validateVertex(v) > 0) return indegree[v];
		else return -1;
	}

	public int outdegree(int v)
	{
		if(validateVertex(v) > 0) return adj[v].size();
		else return -1;
	}

	public Iterable<Integer> adj(int v)
	{
		if(V == 0) return null;
		else return adj[v];
	}

	//Adds directed edge v->w to this graph
	public void addEdge(int v, int w)
	{
		if((validateVertex(v) > 0) && (validateVertex(w) > 0))
		{
			adj[v].add(w);
			indegree[w]++;
			E++;
		}
		else System.out.println("Vertices must be between 0 and " + (V-1));
	}

	//Tests for cycle in graph
	public void findCycle(int v)
	{
		stack[v] = true;
		visited[v] = true;

		for(int w : adj(v))
		{
			if(!visited[w]) findCycle(w);
			else if(stack[w])
			{
				hasCycle = true;
				return;
			}
		}

		stack[v] = false;
	}

	public boolean hasCycle()
	{
		return hasCycle;
	}

	public ArrayList<Integer> BFS(int v)
	{
		boolean[] marked = new boolean[V];
		ArrayList<Integer> order = new ArrayList<Integer>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		marked[v] = true;
		queue.add(v);

		while(queue.size() > 0)
		{
			v = queue.poll();
			order.add(v);

			Iterator<Integer> i = adj[v].listIterator();

			while(i.hasNext())
			{
				int j = i.next();
				if(!marked[j])
				{
					marked[j] = true;
					queue.add(j);
				}
			}
		}

		return order;
	}

	public DAG reverse()
	{
		DAG reverseDAG = new DAG(V);
		for(int v = 0; v < V; v++)
			for(int w : adj(v))
				reverseDAG.addEdge(w, v);

		return reverseDAG;
	}

	public int findLCA(int v, int w)
	{
		findCycle(0);

		if(hasCycle || (validateVertex(v) < 0) || (validateVertex(w) < 0) || E == 0) return -1;

		DAG reverseDAG = reverse();
		ArrayList<Integer> list1 = reverseDAG.BFS(v);
		ArrayList<Integer> list2 = reverseDAG.BFS(w);
		ArrayList<Integer> commonAncestors = new ArrayList<Integer>();
		boolean LCAfound = false;

		for(int i = 0; i < list1.size(); i++)
		{
			for(int j = 0; j < list2.size(); j++)
			{
				if(list1.get(i) == list2.get(j))
				{
					commonAncestors.add(list1.get(i));
					LCAfound = true;
				}
			}
		}

		if(LCAfound) return commonAncestors.get(0);
		else return -1;

	}


























}
