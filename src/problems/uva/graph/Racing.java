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
 * Accepted first attempt 0.526 s. Fairly simple problem, but a little tricky to categorize the problem into a maximum spanning tree
 *
 */
public class Racing {
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
	
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(100001, new Comparator<Edge>() 
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
		int N, M, T=0;
		while((T = MyScanner.readInt()) == -1); //ignore blanks
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1); //ignore blanks
			M = MyScanner.readInt();
			if(N == 0 && M == 0)
				break; //exit
			UnionFind.p = new int[N + 1];
			UnionFind.rank = new int[N + 1];
			UnionFind.init();
			while(M-- > 0)
			{
				Edge e = new Edge();
				e.v1 = MyScanner.readInt();
				e.v2 = MyScanner.readInt();
				e.distance = MyScanner.readInt();
				pq.add(e);
			}
			pw.println(krushkal()); //calculate MST
			pq.clear(); //clear pq
		}
		MyScanner.readInt(); //ignore 0
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Run krushkal's to fetch Minimum Spanning Tree distance
	 * @return
	 */
	private static int krushkal()
	{
		int min = 0;
		while(!pq.isEmpty())
		{
			Edge first = pq.remove();
			if(!UnionFind.union(first.v1, first.v2))
				min += first.distance;
		}
		return min;
	}
}
