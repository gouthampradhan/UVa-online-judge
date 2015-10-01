package problems;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted with 0.372 s using Scanner. Doesn't work with BufferedReader or BufferedInputStream
 *
 */
public class ConnectTheCampus{
	/**
	 * Scanner class
	 * 
	 * @author gouthamvidyapradhan
	 */
	static class MyScanner {
		
		/**
		 * 
		 */
		private static BufferedInputStream bis = new BufferedInputStream(System.in);
		
		private static StringTokenizer st;

		/**
		 * Read integer
		 * 
		 * @return
		 * @throws Exception
		 */
		public static String readNext() throws Exception 
		{
			int in;
			char inChar;
			StringBuffer line = new StringBuffer();
			String token;
			if(st != null && st.hasMoreTokens())
				token = st.nextToken();
			else
			{
				do 
				{
			        in = bis.read();
			        if(in == -1) return null;
			        inChar = (char) in;
			        if((inChar != '\n') && (inChar != '\r'))
			        	line.append(inChar);
			    } while ((in != '\n') && (in != '\r')); 
				if(line.toString().isEmpty())
					return "";
				st = new StringTokenizer(line.toString());
				token = st.nextToken();
			}
			return token.trim();
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
			bis.close();
		}
	}
	
	private static int[] x; //x coordinates
	private static int[] y; //y coordinates
	
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
		int N;
		String str;
		while((str = MyScanner.readNext()) != null)
		{
			if(str.equals("")) continue;
			N = MyScanner.parseInt(str);
			x = new int[N+1]; y = new int[N+1];
			UnionFind.p = new int[N+1];
			UnionFind.rank = new int[N+1];
			UnionFind.init();
			UnionFind.numOfDisjoinSet = N; //inject number of disjoint
			int tail = 1; //tail pointer
			while(N-- > 0)
			{
				int newX = MyScanner.parseInt(MyScanner.readNext());
				int newY = MyScanner.parseInt(MyScanner.readNext());
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
			int M = MyScanner.parseInt(MyScanner.readNext()); //already constructed highways
			while(M-- > 0) 
			{
				int from = MyScanner.parseInt(MyScanner.readNext());
				int to = MyScanner.parseInt(MyScanner.readNext()); 
				UnionFind.union(from, to); //perform union
			}
			double result = krushkal();
			DecimalFormat f = new DecimalFormat("0.00");
			pw.println(f.format(result));
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
		double length = 0.0d;
		while(!pq.isEmpty())
		{
			if(UnionFind.numOfDisjoinSet == 1) //exit condition
				return length;
			Edge first = pq.remove();
			if(UnionFind.union(first.v1, first.v2))
				length+=first.distance;
		}
		return length;
	}
}
