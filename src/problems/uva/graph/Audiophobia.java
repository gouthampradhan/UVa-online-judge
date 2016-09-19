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
 * Accepted in first attempt 0.316 s. The disjoint set used here is a slightly modified version since the vertex begins from index 1.
 * MINMAX - Simple Krushka's + BFS. Used an array of min[] instead of recursive backtracking to fetch the minimum using BFS.
 *
 */
public class Audiophobia 
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
		 * Close BufferedReader
		 * 
		 * @throws Exception
		 */
		public static void close() throws Exception {
			br.close();
		}
	}

	private static int[] max; //array to store the max decibels
	private static List<Integer>[] graph;
	private static final int CONSTANT = 65535;
	private static final String CASE = "Case #";
	private static final String NOPATH = "no path";
	private static int[] q;
	private static int[] sF;
	private static BitSet done = new BitSet();
	private static Queue<Edge> pq = new java.util.PriorityQueue<Edge>(1001, new Comparator<Edge>() 
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
		int Q, C, S, T=0;
		while(true)
		{
			while((C = MyScanner.readInt()) == -1); //ignore blanks
			S = MyScanner.readInt();
			Q = MyScanner.readInt(); //number of queries
			if(C == 0 && S == 0 && Q==0)
				break; //exit
			UnionFind.p = new int[C + 1];
			UnionFind.rank = new int[C + 1];
			UnionFind.numOfDisjoinSet = C; //inject the number of disjoints
			UnionFind.init();
			graph = new List[101];
			sF = new int[2];
			while(S-- > 0)
			{
				Edge e = new Edge();
				e.v1 = MyScanner.readInt();
				e.v2 = MyScanner.readInt();
				e.distance = MyScanner.readInt();
				pq.add(e);
			}
			krushkal(); //calculate MST
			if(T != 0)
				pw.println();
			pw.println(CASE + ++T);
			while(Q-- > 0)
			{
				while((sF[0] = MyScanner.readInt()) == -1); //ignore blanks
				sF[1] = MyScanner.readInt();
				int maxMin = bfs();
				pw.println((maxMin >= 0) ? maxMin : NOPATH);
				done.clear(); //clear bits
			}
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
		List<Integer> children = graph[parent];
		if(children == null)
			children = new ArrayList<>();
		children.add((child << 16) + weight);
		graph[parent] = children;
	}
	/**
	 * Perform bfs to fetch the max decibels
	 * @return
	 */
	private static int bfs()
	{
		if(sF[0] == sF[1])
			return 0;
		int head = 0, tail = 0; q = new int[101];
		q[tail++] = (sF[0] << 16) + CONSTANT; 
		max = new int[101]; max[sF[0]] = 0;
		done.set(sF[0]); //mark the starting vertex as done.
		while(head < tail)
		{
			int first = q[head++];
			int v = first >> 16;
			List<Integer> childern = graph[v];
			if(childern != null)
			{
				for(int c : childern)
				{
					int cW = c & CONSTANT;
					int cV = c >> 16;
					if(!done.get(cV))
					{
						max[cV] = Math.max(max[v], cW);
						if(cV == sF[1])
							return max[cV]; 
						done.set(cV); //mark this vertex as done.
						q[tail++] = c;
					}
				}
			}
		}
		return -1;
	}
}
