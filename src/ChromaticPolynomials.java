import java.util.ArrayList;

public class ChromaticPolynomials {
	static ArrayList<Graph> gList = new ArrayList<Graph>();

	public static void main(String[] args) {
		if(args.length == 0){
			StdOut.println("There are no graphs to process!");
		}
		for (String s : args) {
			In in = new In(s);
			Graph g = new Graph(in);
			gList.add(g);
			String a = s.replaceFirst("[.][A-Za-z]*", "");
			StdOut.println("Graph: "+a);
			StdOut.println(g);
			/*
			g.addEdge(3, 2);
			StdOut.println(g);
			StdOut.println("Remove ");
			g.removeNode(3);
			StdOut.println(g);
			*/
			if(g.isConnectGraph()){
				StdOut.printf("%s is connected!\n",a);
				StdOut.print("P(x) = "+g.px());
			} else {
				StdOut.printf("%s is not connected!",a);
				StdOut.printf("%s has %d components\n",a,g.countComponent());
				StdOut.print("P(x) = "+g.px());
			}
			/********* P(x) *******************/
			
			StdOut.println("\n");
		}
	}
}
