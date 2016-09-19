package problems.uva.graph;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
 * Accepted 0.125 s. Not much of improvement after change in the input reader logic to use BufferedInputStream instead of BufferedReader.
 *
 */
public class Frogger {
	
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
			int in; char inChar; String token;
			if(st != null && st.hasMoreTokens())
				token = st.nextToken();
			else
			{
				StringBuffer line = new StringBuffer();
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

	private static double[] max; //array to store the max distance
	private static List<Stone>[] graph;
	private static final String SCENARIO = "Scenario #";
	private static final String FROG_DISTANCE = "Frog Distance = ";
	private static int[] q;
	private static int[] sF;
	private static BitSet done = new BitSet(500500);
	private static int[] x; //x coordinates
	private static int[] y; //y coordinates
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(500500, new Comparator<Edge>() 
	{
		@Override
		public int compare(Edge e1, Edge e2) 
		{
			return (e1.distance < e2.distance)? -1 : ((e1.distance > e2.distance)? 1 : 0); //Min priority queue
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
	
	
	private static class Stone
	{
		int v;
		double w;
		Stone(int v, double w)
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
		int N, T=0;
		while(true)
		{
			String line;
			while((line = MyScanner.readNext()).equals("")); //ignore blanks
			N = MyScanner.parseInt(line);
			if(N == 0)
				break; //exit
			x = new int[N]; y = new int[N];
			UnionFind.p = new int[N];
			UnionFind.rank = new int[N];
			UnionFind.init();
			graph = new List[201];
			sF = new int[] {0, 1}; // start and finish stone.
			int tail = 0; //tail pointer
			while(N-- > 0)
			{
				int newX = MyScanner.parseInt(MyScanner.readNext());
				int newY = MyScanner.parseInt(MyScanner.readNext());
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
			krushkal(); //calculate MST
			pw.println(SCENARIO + ++T);
			pw.println(FROG_DISTANCE + String.format("%.3f", bfs()));
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
	private static void addChildren(int parent, int child, double weight)
	{
		List<Stone> children = graph[parent];
		if(children == null)
			children = new ArrayList<>();
		Stone v = new Stone(child, weight);
		children.add(v);
		graph[parent] = children;
	}
	
	/**
	 * Perform bfs to fetch the max decibels
	 * @return
	 */
	private static double bfs()
	{
		int head = 0, tail = 0; q = new int[201];
		q[tail++] = sF[0]; 
		max = new double[201]; max[sF[0]] = Integer.MIN_VALUE;
		done.set(sF[0]); //mark the starting vertex as done.
		while(head < tail)
		{
			int first = q[head++];
			List<Stone> childern = graph[first];
			if(childern != null)
			{
				for(Stone c : childern)
				{
					double cW = c.w;
					int cV = c.v;
					if(!done.get(cV))
					{
						max[cV] = Math.max(max[first], cW);
						if(cV == sF[1])
							return max[cV]; 
						done.set(cV); //mark this vertex as done.
						q[tail++] = cV;
					}
				}
			}
		}
		return 0;
	}
}
