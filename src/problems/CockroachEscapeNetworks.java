package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.466 s. Very interesting problem.
 * MyAlgorithm : O(Nx3) solution.
 * 1. Perform BFS for each edge by adding two vertices of an edge in the queue (multi-sources bfs).
 * 2. For each edge on step 1, check the Max value of the farthest vertex, if the max value returned is <= the previous max_value then, do step 3
 * NOTE: Idea here of checking <= is that, the diameter of a tree can be at most two times (x2) max_value returned in step 2 and 
 * if the max_value returned is same or lesser then the there is a possibility that the diameter of that tree could be smaller than previous.
 * 3. Perform a second bfs from the farthest vertex (root returned from step 2) to calculate the diameter.
 * 4. Maintain a minimum diameter and print this value.
 *
 */
public class CockroachEscapeNetworks {

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
                if(str == null) return -2;
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
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
            return br.ready();
        }

        /**
         * Read line
         * @return
         * @throws Exception
         */
        public static String readLine() throws Exception
        {
            return br.readLine();
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

            // Build the number
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int M, N, T, MAX, MIN, MIN_MAX, root;
    private static int[] q, min;
    private static BitSet done = new BitSet();
    private static List<List<Integer>> graph, tree;
    private static final String CASE = "Case #", COLON = ":";

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		int count = 0;
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			M = MyScanner.readInt();
			MIN = Integer.MAX_VALUE; 
			MIN_MAX = (M == 0) ?  0 : Integer.MAX_VALUE;
			graph = new ArrayList<List<Integer>>();
			tree = new ArrayList<List<Integer>>();
			for(int i = 0; i <= N; i++)
				graph.add(i, new ArrayList<Integer>());
			for(int i = 0; i < M; i++)
			{
				int from = MyScanner.readInt();
				int to = MyScanner.readInt();
				graph.get(from).add(to);
				graph.get(to).add(from); //make two way connection
			}
			for(int i = 0; i < N; i++)
			{
				List<Integer> children = graph.get(i);
				if(children.isEmpty()) continue;
				for(int c : children)
				{
					MAX = Integer.MIN_VALUE;
					bfs(graph, i, c);
					MAX++; //increment one
					if(MAX <= MIN)
					{
						MIN = MAX;
						bfs(tree, root); //calculate the diameter
						MIN_MAX = Math.min(MIN_MAX, MAX); //maintain the minimum diameter
					}
				}
			}
 			pw.println(CASE + ++count + COLON);
 			pw.println(MIN_MAX);
 			pw.println();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Bfs to find the diameter and construct a optional tree
	 * @param graph
	 * @param s
	 * @param newTree
	 */
	private static void bfs(List<List<Integer>> graph, int... s)
	{
		q = new int[26]; min = new int[26];
		int head = 0, tail = 0;
		q[tail++] = s[0]; //start vertex
		done.clear();
		done.set(s[0]);  //mark the start vertex as finished
		if(s.length > 1)
		{
			for(int i = 0; i < N; i++)
				tree.add(i, new ArrayList<Integer>()); //clear tree
			q[tail++] = s[1];
			done.set(s[1]);
			tree.get(s[0]).add(s[1]); //include the first edge in the tree
			tree.get(s[1]).add(s[0]);
		}
		while(head < tail)
		{
			int first = q[head++];
			List<Integer> children = graph.get(first);
			for(int c : children)
			{
				if(!done.get(c))
				{
					min[c] = min[first] + 1;
					if(min[c] > MAX)
					{
						MAX = min[c];
						root = c;
					}
					if(s.length > 1)
					{
						//add the edge to tree
						tree.get(first).add(c); 
						tree.get(c).add(first);
					} 
					q[tail++] = c; //add this to queue
					done.set(c); //mark this as finished.
				}
			}
		}
	}
}
