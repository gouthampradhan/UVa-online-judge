package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.179 s. Very good problem, one of the best amongst the MST problems, tricky test cases. Second Best Spanning Tree Algorithm.
 *
 */
public class ACMContestBlackout {
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
		 * Parse to integer
		 * @param in
		 * @return integer value
		 */
		private static int parseInt(String in)
		{
			// Check for a sign.
		    int num  = 0, sign = -1, i = 0;
		    final int len  = in.length( );
		    final char ch  = in.charAt( 0 );
		    if ( ch == '-' )
		        sign = 1;
		    else
		        num = '0' - ch;

		    // Build the number.
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

	private static int N; //Number of vertices
	private static int M; //Number of connections
	private static List<Edge> pq = new ArrayList<>(5050);
	private static int[] minCost;
	private static int i; //index to min array
	private static final String BLANK = " ";
	private static List<Edge> mst = new ArrayList<>(5050);
	private static class EdgeComparator implements Comparator<Edge>
	{

		@Override
		public int compare(Edge e1, Edge e2) 
		{
			return (e1.distance < e2.distance)? -1 : ((e1.distance > e2.distance)? 1 : 0);
		}
		
	}
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
		public boolean equals(Edge e)
		{
			if((this.v1 == e.v1) && (this.v2 == e.v2)) return true;
			return false;
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
		int T;
		while((T = MyScanner.readInt()) == -1);
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1); //ignore blanks
			M = MyScanner.readInt();
			UnionFind.p = new int[N + 1];
			UnionFind.rank = new int[N + 1];
			UnionFind.init();
			UnionFind.numOfDisjoinSet = N; //inject the number of disjoint
			i = 0; //index to min array
			while(M-- > 0)
			{
				Edge e = new Edge();
				e.v1 = MyScanner.readInt();
				e.v2 = MyScanner.readInt();
				e.distance = MyScanner.readInt();
				pq.add(e);
			}
			Collections.sort(pq, new EdgeComparator());
			int l = pq.size();
			int min = krushkal(l);
			minCost = new int[mst.size() + 1];
			Arrays.fill(minCost, Integer.MAX_VALUE); //very important. There is a possibility that after
			//flagging one of the edges the MST may not be formed and in this case the value of that position in minCost would be 0 ! 
			minCost[i++] = min; //add the original minimum to the array
			secondBest(l);
			Arrays.sort(minCost);
			pw.println(minCost[0] + BLANK + minCost[1]);
			pq.clear(); //clear pq
			mst.clear();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Run krushkal's to fetch Minimum Spanning Tree distance
	 * @return
	 */
	private static int krushkal(int l)
	{
		int min = 0;
		for(int ii=0; ii < l; ii++)
		{
			Edge first = pq.get(ii);
			if(UnionFind.union(first.v1, first.v2))
			{
				min+=first.distance;
				mst.add(first); //store mst edges
			}
			if(UnionFind.numOfDisjoinSet == 1) //MST is formed. No need to continue with rest of the queue elements
				return min;
		}
		return 0;
	}
	
	/**
	 * Run krushkal's to fetch Second Best Spanning tree
	 * @return
	 */
	private static void secondBest(int l)
	{
		Iterator<Edge> ite = mst.iterator();
		while(ite.hasNext()) //flag each edge
		{
			int min = 0;
			Edge flagEdge = ite.next();
			UnionFind.p = new int[N + 1];
			UnionFind.rank = new int[N + 1];
			UnionFind.init();
			UnionFind.numOfDisjoinSet = N;
			for(int ii=0; ii < l; ii++)
			{
				Edge first = pq.get(ii);
				if(first.equals(flagEdge)) continue; //ignore the flagged edge
				if(UnionFind.union(first.v1, first.v2))
					min+=first.distance;
				if(UnionFind.numOfDisjoinSet == 1) //MST is formed. No need to continue with rest of the queue elements
				{
					minCost[i++] = min;
					break; 
				}
			}
		}
	}

}
