package problems;

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
 * Accepted with 0.239 s. Simple problem to demonstrate the MSF (minimum spanning forest).
 *
 */
public class ArcticNetwork {
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
	
	private static int S; //number of satellites
	private static int[] x; //x coordinates
	private static int[] y; //y coordinates
	
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(125250, new Comparator<Edge>() 
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
		double distance;
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
			numOfDisjoinSet = p.length;
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
		int T = 0, O; //outposts 
		while((T = MyScanner.readInt()) == -1); //ignore blanks
		while(T-- > 0)
		{
			while((S = MyScanner.readInt()) == -1); //ignore blanks
			O = MyScanner.readInt();
			x = new int[O]; y = new int[O];
			UnionFind.p = new int[O];
			UnionFind.rank = new int[O];
			UnionFind.init();
			int tail = 0; //tail pointer
			while(O-- > 0)
			{
				int newX = MyScanner.readInt();
				int newY = MyScanner.readInt();
				for(int i=0; i < tail; i++)
				{
					double dist = Math.sqrt(Math.pow(newX - x[i], 2) + Math.pow(newY - y[i], 2));
					Edge e = new Edge();
					e.v1 = i;
					e.v2 = tail;
					e.distance = dist;
					pq.add(e);
				}
				x[tail] = newX; //include the new coordinates at the rear
				y[tail++] = newY;
			}
			pw.println(String.format("%.2f", krushkal()));
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
	private static double krushkal()
	{
		double max = 0.0d;
		while(!pq.isEmpty())
		{
			Edge first = pq.remove();
			if(UnionFind.union(first.v1, first.v2))
			{
				max = (max > first.distance) ? max : first.distance;
				if(UnionFind.numOfDisjoinSet == S) //exit as soon as the number of disjoint sets are equal to number of satellites
					return max;
			}
		}
		return max;
	}
}
