package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * <ul>
 *  <li>
 *  	Accepted in first attempt, 0.546 s. Could be faster have to check if we can still improve UnionFind to give faster performance.
 *  </li>
 *  <li>
 * 		Rank 912 out of 1135 is a bit on the lower side.
 *  </li>
 *  <li>
 * 		The union find used in this problem is slightly modified version because the vertices starts from 1 and not 0.
 *  </li>
 * </ul>
 *
 */
public class Airports {
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

	private static int C; //Cost
	private static int A; //Airport cost
	private static int N; //Number of vertices
	private static int M; //Number of connections
	private static int NA; //Total number of airports
	private static int msd;
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(100001, new Comparator<Edge>() 
	{
		@Override
		public int compare(Edge e1, Edge e2) 
		{
			return (e1.distance < e2.distance)? -1 : ((e1.distance > e2.distance)? 1 : 0);
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
		int T = 0, count = 0;
		while((T = MyScanner.readInt()) == -1); //ignore blanks
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1); //ignore blanks
			M = MyScanner.readInt();
			NA = N; msd = 0;//initialize
			A = MyScanner.readInt(); //Airport cost
			C = N * A; //initialize the total cost
			UnionFind.p = new int[N + 1];
			UnionFind.rank = new int[N + 1];
			UnionFind.numOfDisjoinSet = N;
			UnionFind.init();
			while(M-- > 0)
			{
				Edge e = new Edge();
				e.v1 = MyScanner.readInt();
				e.v2 = MyScanner.readInt();
				e.distance = MyScanner.readInt();
				pq.add(e);
			}
			krushkal();
			pw.println("Case #" + (++count) + ": " + C + " " + NA);
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
			int newMin;
			if(UnionFind.union(first.v1, first.v2))
			{
				msd += first.distance;
				NA = UnionFind.numOfDisjoinSet;
				newMin = (NA * A) + msd;
				if(C <= newMin) //Check if old total cost is lt newMin.
				{
					NA += 1; //Previous airport count
					break;
				}
				C = newMin;
				if(NA == 1)
					break;
			}
		}
	}
}
