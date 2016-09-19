package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 2.199s. Probably one of the toughest problems I've ever solved so far in Uva Online Judge. 
 * MyAlgorithm O(N) + O(Nx2) : 
 * 1. First calculate the diameter of a tree and save the entire path (edges) of the tree along the diameter in an array.
 * 2. Now, iterate through the array of edges, remove the edge from the graph and calculate the diameter of both the sub-trees.
 * 3. Calculate the mid-point of each of the sub-tree and calculate the new diameter considering the new edge to be added along the mid point of each of the sub-tree.
 * 4. Save the new diameter, deleted edge and new mid-points where the new edge is to be included in a HashMap with new diameter as the key.  
 * NOTE: Don't have to add the new edge to the graph but instead use the formula to measure the new diameter.
 * let d1 = diameter of subTree 1
 * let d2 = diameter of subTree 2
 * then, new diameter would be Maximum of [(d1/2) + (d1 % 2) + (d2 / 2) + (d2 % d) + 1, Maximum (d1, d2)];
 * example: if diameter of a subTree is 3, then 3/2 = 1 (1.5, decimal ignored). Hence to pick the right mid-point, since 1.5 can't be chosen as mid-point
 * I do 3/2 + 3%2 which is equal to 2;
 * 4. Maintain a minimum diameter.
 * 5. Pick the value from HashMap for the minimum diameter and print the result.
 *
 */
public class FlightPlanning {

    /**
     * Scanner class
     *
     * @author gouthamvidyapradhan
     */
    static class MyScanner {
        /**
         * Buffered reader
         */
        private static BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        private static StringTokenizer st;

        /**
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static int readInt() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return parseInt(st.nextToken());
                }
                String str = br.readLine();
                if(str == null) return -2;
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return parseInt(st.nextToken());
                }
            } catch (IOException e) {
                close();
                return -1;
            }
            return -1;
        }

        /**
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
            return br.ready();
        }

        /**
         * Read line
         * @return
         * @throws Exception
         */
        public static String readLine() throws Exception
        {
            return br.readLine();
        }
        /**
         * Parse to integer
         * @param in
         * @return integer value
         */
        public static int parseInt(String in)
        {
            // Check for a sign.
            int num  = 0, sign = -1, i = 0;
            final int len  = in.length( );
            final char ch  = in.charAt( 0 );
            if ( ch == '-' )
                sign = 1;
            else
                num = '0' - ch;

            // Build the number
            i+=1;
            while ( i < len )
                num = num*10 + '0' - in.charAt( i++ );
            return sign * num;
        }

        /**
         * Close BufferedReader
         *
         * @throws Exception
         */
        public static void close() throws Exception {
            br.close();
        }
    }

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int N, T, start, end, root, L, MIN;
    private static int[] P;
    private static BitSet done = new BitSet();
    private static List<List<Integer>> graph;
    private static Map<Integer, List<Edge>> minMapping;
    private static final String BLANK = " ";

    /**
     * Edge class
     * @author gouthamvidyapradhan
     *
     */
    private static class Edge
    {
    	int v1; 
    	int v2;
    	Edge(int v1, int v2)
    	{
    		this.v1 = v1;
    		this.v2 = v2;
    	}
    }
    
    /**
     * Tree class to store the property of a subTree. 
     * @author gouthamvidyapradhan
     *
     */
    private static class Tree
    {
    	int start; //start vertex of a subTree.
    	int end; //end vertex of a subTree.
    	int diameter; //diameter of a tree.
    	List<Edge> path; //path of the diameter.
    }
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			L = Integer.MIN_VALUE;
			P = new int[N + 1];
			MIN = Integer.MAX_VALUE;
			graph = new ArrayList<>();
			minMapping = new HashMap<>();
			for(int i = 0; i <= N; i++)
				graph.add(i, new ArrayList<Integer>()); //initialize
			for(int i = 1; i < N; i++)
			{
				int from = MyScanner.readInt();
				int to = MyScanner.readInt();
				graph.get(from).add(to);
				graph.get(to).add(from); // make two way connection
			}
			Tree tree = getDiameter(1);
			start = tree.start;
			end = tree.end;
			List<Edge> subTree = tree.path;
			for(int i = 0, l = subTree.size(); i < l; i++)
			{
				Edge deleteEdge = subTree.get(i);
				calculateMinDiameter(deleteEdge);
			}
			List<Edge> edges = minMapping.get(MIN);
			pw.println(MIN);
			pw.println(edges.get(0).v1 + BLANK + edges.get(0).v2); //edge to be deleted
			pw.println(edges.get(1).v1 + BLANK + edges.get(1).v2); //new edge
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Method breaks the graph into two parts, calculates the new diameter for the tree considering the new edge which is to be 
	 * included along the midpoints of each subTree.
	 * @param deleteEdge
	 * @return
	 */
	private static void calculateMinDiameter(Edge edge)
	{
		deleteEdge(edge); //edge to be deleted
		Tree subTree1 = getDiameter(start);
		int l1 = subTree1.diameter;
		int m1 = (l1 / 2) + (l1 % 2); //mid-length

		Tree subTree2 = getDiameter(end);
		int l2 = subTree2.diameter;
		int m2 = (l2 / 2) + (l2 % 2); //mid-length

		List<Edge> path1 = subTree1.path; //path of new subtree1
		List<Edge> path2 = subTree2.path; //path of new subtree2
		int newDia = Math.max(Math.max(l1,  l2), m1 + m2 + 1);
		if(newDia < MIN)
		{
			MIN = newDia;
			int mp1 = (m1 == 0) ? start : path1.get(m1 - 1).v2; //if the mid-point is 0 then, the start node itself is the midpoint because there is no edge !
			int mp2 = (m2 == 0) ? end : path2.get(m2 - 1).v2;
			List<Edge> edges = minMapping.get(newDia);
			if(edges == null)
				edges = new ArrayList<Edge>();
			edges.add(edge); //add the deleted edge
			edges.add(new Edge(mp1, mp2)); //add the new edge.
			minMapping.put(newDia, edges);
		}
		graph.get(edge.v1).add(edge.v2);
		graph.get(edge.v2).add(edge.v1); //restore the deleted vertex
	}
	
	/**
	 * Deletes the edge in a given graph.
	 * @param e edge to be deleted
	 */
	private static void deleteEdge(Edge e)
	{
		List<Integer> children1 = graph.get(e.v1);
		for(int i = 0, l = children1.size(); i < l; i++)
		{
			if(children1.get(i) == e.v2)
			{
				children1.remove(i); //remove child
				break;
			}
		}
		List<Integer> children2 = graph.get(e.v2);
		for(int i = 0, l = children2.size(); i < l; i++)
		{
			if(children2.get(i) == e.v1)
			{
				children2.remove(i); //remove child
				break;
			}
		}
	}
	
	/**
	 * Method calculates the diameter starting from the given vertex.
	 * @param ele
	 * @return Tree object
	 */
	private static Tree getDiameter(int ele)
	{
		Tree tree = new Tree();
		done.clear();
		L = Integer.MIN_VALUE;
		P = new int[N + 1]; //create a new parent array
		dfs(ele, 0);
		L = Integer.MIN_VALUE;
		P = new int[N + 1]; //create a new parent array
		tree.start = root; //start node
		done.clear();
		dfs(root, 0);
		tree.end = root; //end node
		tree.diameter = L;
		tree.path = getPath(root);
		return tree;
	}
	
	/**
	 * Dfs to find the diameter and calculate the farthest node and its distance. 
	 * @param ele
	 * @param count
	 */
	private static void dfs(int ele, int count)
	{
		done.set(ele); //mark this as visited
		List<Integer> children = graph.get(ele);
		for(int c : children)
		{
			if(!done.get(c))
			{
				P[c] = ele; //save the parent
				dfs(c, count + 1);
			}
		}
		if(count > L) 
		{
			L = count;
			root = ele;
		}
	}

	/**
	 * Return the path of the given node.
	 * @param root
	 * @return List of nodes
	 */
	private static List<Edge> getPath(int root)
	{
		List<Edge> subTree = new ArrayList<>();
		while(P[root] != 0)
		{
			subTree.add(new Edge(P[root], root));
			root = P[root];
		}
		Collections.reverse(subTree);
		return subTree;
	}
}
