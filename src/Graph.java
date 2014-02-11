import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*************************************************************************
 *  Compilation:  javac Graph.java        
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges 
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9 
 *
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges 
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15 
 *  1: 220 203 200 194 189 164 150 130 107 72 
 *  2: 141 110 108 86 79 51 42 18 14 
 *  ...
 *  
 *************************************************************************/

/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  named 0 through V-1.
 *  It supports the following operations: add an edge to the graph,
 *  iterate over all of the neighbors adjacent to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/51undirected">Section 5.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

/**********************
 * 
 * 
 * modified by SephyZhou
 * 
 */
public class Graph {
	private int V;
	private int E;
	private HashMap<Integer, SET<Integer>> adj;
	// private SET<Integer>[] adj;
	private CC cc;

	/**
	 * Create an empty graph with V vertices.
	 */
	public Graph(int V) {
		//this is wrong. if 1,2,4 then it will produce 1,2,3. not correct at all
		if (V < 0)
			throw new RuntimeException("Number of vertices must be nonnegative");
		this.V = V;
		// StdOut.println("V is "+V);
		this.E = 0;
		adj = new HashMap<Integer, SET<Integer>>();
		// adj = (SET<Integer>[]) new SET[V];
		for (int v = 0; v < V; v++) {
			// adj[v] = new SET<Integer>();
			adj.put(v, new SET<Integer>());
		}
	}

	/**
	 * Create a random graph with V vertices and E edges. Expected running time
	 * is proportional to V + E.
	 */
	public Graph(int V, int E) {
		this(V);
		if (E < 0)
			throw new RuntimeException("Number of edges must be nonnegative");
		for (int i = 0; i < E; i++) {
			int v = (int) (Math.random() * V);
			int w = (int) (Math.random() * V);
			addEdge(v, w);
		}
	}

	/**
	 * Create a di-graph from input stream.
	 */
	public Graph(In in) {
		this(in.readInt());
		int E = in.readInt();
		// StdOut.println("E is "+E);
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			addEdge(v, w);
		}
		edgeCount();
		cc = new CC(this);
	}

	public void edgeCount() {
		int newE = 0;
		//for (int i = 0; i < adj.size(); i++) {
		for(Integer i : adj.keySet()){
			// for (int j = 0; j < adj[i].size(); j++) {
			// StdOut.printf("DEBUG: i is %d\n",i);
			for (int j = 0; adj.get(i) != null && j < adj.get(i).size(); j++) {
				// StdOut.printf("DEBUG: j is %d\n",j);
				newE++;
			}
		}
		this.E = newE / 2;
	}
	
	
	/**
	 * Copy constructor.
	 */
	public Graph(Graph G) {
		//this(G.V());
		this(G.adj.keySet());
		this.E = G.E();
		//for (int v = 0; v < G.V(); v++) {
		
		//StdOut.println("DEBUG************************************");
		//StdOut.print(G);
		//StdOut.println("DEBUGFINISHED************************************");
		for(int v : G.adj.keySet()){
		
			// reverse so that adjacency list is in same order as original
			Stack<Integer> reverse = new Stack<Integer>();

			// for (int w : G.adj[v]) {
			for (int w : G.adj.get(v)) {
				reverse.push(w);
			}

			for (int w : reverse) {
				// adj[v].add(w);
				adj.get(v).add(w);
			}
		}
	}

	public Graph(Set<Integer> set) {
		this.V = set.size();
		// StdOut.println("V is "+V);
		this.E = 0;
		adj = new HashMap<Integer, SET<Integer>>();
		//for (int v = 0; v < V; v++) {
		Iterator<Integer> i = set.iterator();
		//for(int v : set.)
		while(i.hasNext()){
			int v = i.next();
			adj.put(v, new SET<Integer>());
		}
	}

	/**
	 * Return the number of vertices in the graph.
	 */
	public int V() {
		return adj.size();
	}

	/**
	 * Return the number of edges in the graph.
	 */
	public int E() {
		return E;
	}

	/**
	 * Add the edge v-w to graph.
	 */
	public void addEdge(int v, int w) {
		//E++;
		// adj[v].add(w);
		// adj[w].add(v);
		adj.get(v).add(w);
		adj.get(w).add(v);
		edgeCount();
	}

	public void removeEdge(int v, int w) {
		//E--;
		adj.get(v).delete(w);
		adj.get(w).delete(v);
		// adj[v].delete(w);
		// adj[w].delete(v);
		edgeCount();
	}

	public void removeNode(int v) {
		if (adj.containsKey(v)) {
			adj.remove(v);
			for (Integer i : adj.keySet()) {
				for (Iterator<Integer> j = adj.get(i).iterator(); j.hasNext();) {
					Integer element = j.next();
					if (element == v) {
						j.remove();
					}
				}
			}
			V--;
		}
		edgeCount();
	}

	/**
	 * Return the list of neighbors of vertex v as in Iterable.
	 */
	public Iterable<Integer> adj(int v) {
		return adj.get(v);
	}

	/**
	 * Return a string representation of the graph. This method will be execute
	 * when doing any StdOut.print
	 */
	public String toString() {
		// is a node is exist is not is important
		StringBuilder s = new StringBuilder();
		// StdOut.print("***************");
		String NEWLINE = System.getProperty("line.separator");
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		int loopNumber = V;
		for (int v = 0; v < loopNumber; v++) {
			if (adj.get(v) != null) {
				s.append(v + ": ");
				for (int w : adj.get(v)) {
					s.append(w + " ");
				}
				s.append(NEWLINE);
			} else {
				// this node itself is empty, so it should be skipped
				loopNumber++;
			}

		}

		return s.toString();
	}

	public String px() {
		if (isDensityOver50()) {
			//StdOut.println("Doing Plus mode since density over 50.");
			return pxPlus(this).toString();
		}
		//StdOut.println("Doing Minus mode since density under 50.");
		return pxMinus(this).toString();
	}

	private Polynomial pxMinus(Graph g) {
		// P(G, x) = P(G - uv, x) - P(Guv, x)
		// select a random uv from graph g
		if (!g.isEmpty()) {
			int[] randomEdge = g.randomEdge();
			int v = randomEdge[0];
			int w = randomEdge[1];
			// get G - uv
			Graph newG = new Graph(g);
			newG.removeEdge(v, w);
			// get Guv
			Graph newG2;
			newG2 = guv(g, v, w);
			//StdOut.printf("***P(G, x) = P(G - %d*%d, x) - P(Guv, x)***\n", v, w);
			//StdOut.println(g);
			//StdOut.println(newG);
			//StdOut.println(newG2);
			//StdOut.println("******");
			// for a pair of adjacent vertices u and v the graph Guv is obtained
			// by merging the two vertices and removing the edge between them.
			return pxMinus(newG).minus(pxMinus(newG2));
		}

		// it's it's empty then return the corresponding Polynomail
		return new Polynomial(1, g.V());
	}

	private Graph guv(Graph g, int v, int w) {
		Graph newG = new Graph(g);
		// 1. add a new vertex n
		// 2. copy all edge link to v & w to n
		// 3. delete all edge related to v&w, including the one linking v & w
		// 4. delete v&w vertex

		// another approach
		// 1.copy all edges of w-n to v-n.
		// those from w but also those to w, vise versa)
		// 2.remove all edge related to w, including the one linking v & w
		// 3.delete vertex w
		// ***** modify newG!!!
		Iterator<Integer> itr = newG.adj.get(w).iterator();
		// for (int n : newG.adj[w]) {
		while (itr.hasNext()) {
			int n = (Integer) itr.next();
			if (n != v) {
				//StdOut.printf("add \tv = %d and n = %d and vise versa \n", v, n);
				newG.addEdge(v, n);
			}
			// StdOut.printf("remove \tn = %d and w = %d\n", n, w);
			// newG.removeEdge(n, w);
			//StdOut.println(newG.toString());
		}
		newG.removeNode(w);
		//StdOut.println("done!!!");
		//StdOut.println(newG.toString());
		return newG;
	}

	private boolean isEmpty() {
		edgeCount();
		if (E <= 0) {
			return true;
		}
		return false;
	}

	private int[] randomEdge() {
		// adj[v].delete(w);
		int v = -1;
		int w = -1;
		//for (int i = V - 1; i >= 0 && !set; i--) {
		for(int i : adj.keySet()){
			for(int j : adj.keySet()){
			//for (int j = V - 1; j >= 0 && !set; j--) {
				// choose the first exist edge and remove it
				// if(adj[i].i)
				if (adj.get(i).contains(j)) {
					v = i;
					w = j;
				}
			}
		}
		int[] result = { v, w };
		return result;
	}

	private Polynomial pxPlus(Graph g) {
		// P(G, x) = P(G + uv, x) + P(Guv, x)
		// select a random uv from graph g
		if (!g.isComplete()) {  //<------------is completed
			int[] randomEdge = g.selectNonExistEdge();
			int v = randomEdge[0];
			int w = randomEdge[1];
			// get G - uv
			Graph newG = new Graph(g);
			newG.addEdge(v, w);   // <----------------add Edge
			// get Guv
			Graph newG2;
			newG2 = guv(g, v, w);
			//StdOut.printf("***P(G, x) = P(G + %d*%d, x) + P(Guv, x)***\n", v, w);
			//StdOut.println(g);
			//StdOut.println(newG);
			//StdOut.println(newG2);
			//StdOut.println("******");
			// for a pair of adjacent vertices u and v the graph Guv is obtained
			// by merging the two vertices and removing the edge between them.
			return pxPlus(newG).plus(pxPlus(newG2));
		}

		// it's it's empty then return the corresponding Polynomail
		//return new Polynomial(1, g.adj.size());
		Polynomial poly = Polynomial.factorial(g.V());
		//StdOut.println("test!!!!!!!"+poly);
		return poly;
	}
	
	private int[] selectNonExistEdge(){
		for(int i: adj.keySet()){
			for(int j : adj.keySet()){
				if(i!=j && !isLinked(i,j)){
					int[] r= {i,j};
					return r;
				}
			}
		}
		return null;
	}

	private boolean isLinked(int i, int j){
		if(adj.get(i).contains(j)){
			return true;
		}
		return false;
	}
	public boolean isComplete(){
		for(int v:adj.keySet()){
			//StdOut.println("*****DEBUG: ");
			//StdOut.printf("v is %d, total size is %d\n", v, adj.size());
			if(adj.get(v).size()+1 < adj.size()){
				return false;
			}
		}
		return true;
	}
	
	
	public double density() {
		// Density = 2*|E| / |V| * (|V| - 1)
		return 100.0 * E / (V * (V - 1));
	}

	public boolean isDensityOver50() {
		if (density() > 50.0) {
			return true;
		}
		return false;
	}

	public boolean isConnectGraph() {
		if (cc.count() > 1) {
			return false;
		}
		return true;
	}

	public int countComponent() {
		return cc.count();
	}
}
