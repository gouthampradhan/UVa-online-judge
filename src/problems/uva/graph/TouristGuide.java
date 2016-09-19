package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * 
 * Accepted on 3rd attempt 0.195 s. My mistakes as as below.
 *  1. Used << and & operation to combine two numbers without knowing the upper bound limit of numbers. DO NOT DO THIS when you don't know the upper limit of input
 *  2. Using floating point numbers and Math.ceil operation to round to next integer - For long integers this will give incorrect result
 *  example 654254568 / 9999 = 65432 but using floating point numbers and Math.ceil operation will give a result 65433 !!
 *  solution : (a + b -1) / b
 *  3. Not printing a blank line after the last output case. The description says print a blank line after every case. 
 *
 */
public class TouristGuide {
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
					return Integer.parseInt(st.nextToken());
				}
				String str = br.readLine();
				if (str != null && !str.trim().equals("")) {
					st = new StringTokenizer(str);
					return Integer.parseInt(st.nextToken());
				}
			} catch (IOException e) {
				close();
				return -1;
			}
			return -1;
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

	private static int[] min; //array to store the min passengers
	private static List<City>[] graph;
	private static final String SCENARIO = "Scenario #";
	private static final String MIN_TRIP = "Minimum Number of Trips = ";
	private static int[] q;
	private static int[] sF;
	private static int P;
	private static BitSet done = new BitSet(25000);
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(25000, new Comparator<Edge>() 
	{
		@Override
		public int compare(Edge e1, Edge e2) 
		{
			return (e1.distance < e2.distance)? 1 : ((e1.distance > e2.distance)? -1 : 0); //Max priority queue
		}
	});
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	
	/**
	 * @author gouthamvidyapradhan
	 * Edge class to store the vertices and distance
	 *
	 */
	private static class Edge
	{
		int v1;
		int v2;
		int distance;
	}
	
	
	private static class City
	{
		int v;
		int w;
		City(int v, int w)
		{
			this.v = v;
			this.w = w;
		}
	}
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Class to represent UnionFind Disjoint Set 
	 *
	 */
	private static class UnionFind
	{
		static int[] p;
		static int[] rank;
		static int numOfDisjoinSet;
		/**
		 * Initialize with its same index as its parent
		 */
		public static void init()
		{
			for(int i=0; i<p.length; i++)
				p[i] = i;
		}
		/**
		 * Find the representative vertex
		 * @param i
		 * @return
		 */
		private static int findSet(int i)
		{
			if(p[i] != i)
				p[i] = findSet(p[i]);
			return p[i];
		}
		/**
		 * Perform union of two vertex
		 * @param i
		 * @param j
		 * @return true if union is performed successfully, false otherwise
		 */
		public static boolean union(int i, int j)
		{
			int x = findSet(i);
			int y = findSet(j);
			if(x != y)
			{
				if(rank[x] > rank[y])
					p[y] = p[x];
				else 
				{
					p[x] = p[y];
					if(rank[x] == rank[y])
						rank[y]++; //increment the rank
				}
				numOfDisjoinSet --;
				return true;
			}
			return false;
		}
	}
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		int N, R, T=0;
		while(true)
		{
			boolean hasEdge = false;
			while((N = MyScanner.readInt()) == -1); //ignore blanks
			R = MyScanner.readInt();
			if(N == 0 && R == 0)
				break; //exit
			UnionFind.p = new int[N + 1];
			UnionFind.rank = new int[N + 1];
			UnionFind.numOfDisjoinSet = N; //inject the number of disjoints
			UnionFind.init();
			graph = new List[101];
			sF = new int[2];
			while(R-- > 0)
			{
				hasEdge = true;
				Edge e = new Edge();
				e.v1 = MyScanner.readInt();
				e.v2 = MyScanner.readInt();
				e.distance = MyScanner.readInt();
				pq.add(e);
			}
			krushkal(); //calculate MST
			pw.println(SCENARIO + ++T);
			if(hasEdge)
			{
				while((sF[0] = MyScanner.readInt()) == -1); //ignore blanks
				sF[1] = MyScanner.readInt();
				P = MyScanner.readInt();
				int maxMin = bfs();
				maxMin -= 1;
				int trips = ((P + maxMin) - 1) / maxMin;
				pw.println(MIN_TRIP + trips);
			}
			else
				pw.println(MIN_TRIP + 0);
			pw.println();
			done.clear(); //clear bits
			pq.clear(); //clear pq
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Run krushkal's to fetch Minimum Spanning Tree distance
	 * @return
	 */
	private static void krushkal()
	{
		while(!pq.isEmpty())
		{
			Edge first = pq.remove();
			if(UnionFind.union(first.v1, first.v2))
			{
 				//if the vertices are part of MST then include this in the graph to be traversed later
				addChildren(first.v1, first.v2, first.distance);
				addChildren(first.v2, first.v1, first.distance); //make two way connection
			}
			if(UnionFind.numOfDisjoinSet == 1) //MST is formed. No need to continue with rest of the queue elements
				return;
		}
	}
	
	/**
	 * Add children
	 * @param parent
	 * @param child
	 */
	private static void addChildren(int parent, int child, int weight)
	{
		List<City> children = graph[parent];
		if(children == null)
			children = new ArrayList<>();
		City v = new City(child, weight);
		children.add(v);
		graph[parent] = children;
	}
	/**
	 * Perform bfs to fetch the max decibels
	 * @return
	 */
	private static int bfs()
	{
		if(sF[0] == sF[1])
			return P+1;
		int head = 0, tail = 0; q = new int[101];
		q[tail++] = sF[0]; 
		min = new int[101]; min[sF[0]] = Integer.MAX_VALUE;
		done.set(sF[0]); //mark the starting vertex as done.
		while(head < tail)
		{
			int first = q[head++];
			List<City> childern = graph[first];
			if(childern != null)
			{
				for(City c : childern)
				{
					int cW = c.w;
					int cV = c.v;
					if(!done.get(cV))
					{
						min[cV] = Math.min(min[first], cW);
						if(cV == sF[1])
							return min[cV]; 
						done.set(cV); //mark this vertex as done.
						q[tail++] = cV;
					}
				}
			}
		}
		return -1;
	}
}
