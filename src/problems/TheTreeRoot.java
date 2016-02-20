package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.465 s. Simple problem. 
 * 
 * My algorithm : Find the diameter and add all the vertices with maximum count in a array. 
 * This requires two DFS passes (first to find the farthest node from the starting vertex and again to find the second set of farthest nodes starting 
 * from anyone of the farthest nodes found during the first pass). 
 * Now, after the second pass we can get a complete list of worst roots and keep a first set of nodes based on median count i.e (max_count / 2). 
 * Perform a third DFS from any worst node found during second pass and keep a second set of nodes based on median count. The intersection of set 1 and set 2
 * would be the best roots.  
 *
 */
public class TheTreeRoot {

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
    private static int D, N;
    private static BitSet done = new BitSet();
    private static Map<Integer, List<Integer>> worstMap = new HashMap<Integer, List<Integer>>();
    private static List<Integer> worst = new ArrayList<>(); 
    private static Set<Integer> best1 = new HashSet<Integer>();
    private static Set<Integer> best2 = new HashSet<Integer>();
    private static List<List<Integer>> graph;
    private static final String BEST = "Best Roots  :", WORST = "Worst Roots :", BLANK = " ";

    /**
     * 
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == -2) break;
			D = Integer.MIN_VALUE;
			graph = new ArrayList<List<Integer>>(N + 1);
			for(int i = 0; i<=N; i++)
				graph.add(new ArrayList<Integer>());
			for(int i = 1; i<=N; i++)
			{
				int nOfChildren = MyScanner.readInt();
				for(int j = 0; j < nOfChildren; j++)
					graph.get(i).add(MyScanner.readInt());
			}
			dfs(1, 0); //dfs 1
			List<Integer> worstLst1 = worstMap.get(D);
			clear();
			dfs(worstLst1.get(0), 0); //dfs 2
			List<Integer> worstLst2 = worstMap.get(D);
			worst.addAll(worstLst1);
			for(int e : worstLst2)
				if(!worst.contains(e))
					worst.add(e); //ignore duplicates
			Collections.sort(worst); //sort worst list in ascending order
			best1.addAll(worstMap.get(D / 2));
			if((D % 2) == 1)
				if(worstMap.get((D / 2) + 1) != null)
					best1.addAll(worstMap.get((D / 2) + 1));
			clear();
			dfs(worstLst2.get(0), 0); //dfs 3
			best2.addAll(worstMap.get(D / 2));
			if((D % 2) == 1)
				if(worstMap.get((D / 2) + 1) != null)
					best2.addAll(worstMap.get((D / 2) + 1));
			pw.print(BEST);
			for(int i = 1; i <= N; i++)
				if(best1.contains(i) && best2.contains(i))
					pw.print(BLANK + i);
			pw.println();
			pw.print(WORST);
			for(int w : worst)
				pw.print(BLANK + w);
			pw.println();
			clear(); worst.clear(); best1.clear(); best2.clear();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Clear
	 */
	private static void clear()
	{
		D = Integer.MIN_VALUE; done.clear(); worstMap.clear();
	}
	/**
	 * Dfs to find the diameter. 
	 * @param ele
	 * @param count
	 */
	private static void dfs(int ele, int count)
	{
		done.set(ele); //mark this as visited
		List<Integer> children = graph.get(ele);
		for(int c : children)
		{
			if(!done.get(c))
				dfs(c, count + 1);
		}
		if(count >= D) 
			D = count;
		if(worstMap.get(count) == null)
			worstMap.put(count, new ArrayList<Integer>());
		worstMap.get(count).add(ele);
	}
}
