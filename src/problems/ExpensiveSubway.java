package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted in first attempt 0.596 s. Rank 145 out of 900+, I think its pretty good ranking. Removing return value from krushkal's helped improve timing. 
 *
 */
public class ExpensiveSubway 
{
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
		 * Read string
		 * 
		 * @return
		 * @throws Exception
		 */
		public static String readString() throws Exception {
			if (st != null && st.hasMoreTokens()) {
				return st.nextToken();
			}
			String str = br.readLine();
			if (str != null) {
				if (str.trim().equals(""))
					return null;
				st = new StringTokenizer(str);
				return st.nextToken();
			}
			return null;
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

	private static int N; //Number of vertices
	private static int M; //Number of connections
	private static int msd; //minimum distance
	private static Map<String, Integer> cityIndex = new HashMap<String, Integer>(400);
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(79801, new Comparator<Edge>() 
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
		while(true)
		{
			while((N = MyScanner.readInt()) == -1); //ignore blanks
			while((M = MyScanner.readInt()) == -1); //ignore blanks
			if(N == 0 && M == 0)
				break;
			msd = 0; int index = 0;//initialize
			UnionFind.p = new int[N];
			UnionFind.rank = new int[N];
			UnionFind.init();
			while(N-- > 0)
			{
				String station;
				while((station = MyScanner.readString()) == null); //ignore blanks
				if(cityIndex.get(station) == null)
					cityIndex.put(station, index++); //Construct city index mapping
			}
			while(M-- > 0)
			{
				Edge e = new Edge();
				String from;
				String to;
				while((from = MyScanner.readString()) == null); //ignore blanks
				to = MyScanner.readString();
				e.v1 = cityIndex.get(from);
				e.v2 = cityIndex.get(to);
				e.distance = MyScanner.readInt();
				pq.add(e);
			}
			MyScanner.readString(); //Don't know why this info is required. Ignore
			krushkal();
			pw.println((UnionFind.numOfDisjoinSet == 1) ? msd : "Impossible");
			pq.clear(); //clear pq
			cityIndex.clear(); //clear index
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
				msd += first.distance;
			if(UnionFind.numOfDisjoinSet == 1) //MST is formed. No need to continue with rest of the queue elements
				break;
		}
	}
}
