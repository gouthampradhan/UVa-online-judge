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
 * 
 * Accepted in first attempt 0.369 s. Interesting problem, but simple solution. Minimum Spanning Subtree (MSS)
 *
 */
public class Highways {
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
	
	private static int[] x; //x coordinates
	private static int[] y; //y coordinates
	private static final String NO_HIGHWAY = "No new highways need";
	private static final String BLANK = " ";
	
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(281625, new Comparator<Edge>() 
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
		int T = 0, N, caseNr = 0; //number of towns 
		while((T = MyScanner.readInt()) == -1); //ignore blanks
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1); //ignore blanks
			x = new int[N+1]; y = new int[N+1];
			UnionFind.p = new int[N+1];
			UnionFind.rank = new int[N+1];
			UnionFind.init();
			UnionFind.numOfDisjoinSet = N; //inject number of disjoint
			int tail = 1; //tail pointer
			while(N-- > 0)
			{
				int newX = MyScanner.readInt();
				int newY = MyScanner.readInt();
				for(int i=1; i < tail; i++)
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
			int M = MyScanner.readInt(); //already constructed highways
			while(M-- > 0) 
			{
				int from = MyScanner.readInt();
				int to = MyScanner.readInt();
				UnionFind.union(from, to); //perform union
			}
			if(caseNr != 0)
				pw.println(); //print blank line in between tests 
			if(!krushkal())
				pw.println(NO_HIGHWAY);
			pq.clear(); //clear pq
			caseNr++; //increment case nr
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Run krushkal's to fetch Minimum Spanning Tree distance
	 * @return
	 */
	private static boolean krushkal()
	{
		boolean newHigway = false;
		while(!pq.isEmpty())
		{
			if(UnionFind.numOfDisjoinSet == 1) //exit condition
				return newHigway;
			Edge first = pq.remove();
			int from = first.v1; int to = first.v2; 
			if(UnionFind.union(from, to))
			{
				newHigway = true;
				pw.println(from + BLANK + to);
			}
		}
		return newHigway;
	}
}
