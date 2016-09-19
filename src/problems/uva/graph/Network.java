package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted : 0.268 s. Implementation of Trajan's algorithm of finding articulation points, was very tricky, worked on it for two days !  
 *
 */
public class Network {

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
                if ((str != null) && !str.trim().equals("")) {
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
    
    private static List<Set<Integer>> graph;
    private static int N, root, next, rootChildCount;
    private static int[] dfs_low, dfs_num, dfs_parent; 
    private static boolean[] done;
    private static Set<Integer> articulationPoints = new HashSet<>();
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
	{
    	while(true)
    	{
	    	while((N = MyScanner.readInt()) == -1);
	    	if(N == 0) break;
    		graph = new ArrayList<>(N+1);
	    	for(int i=0, l = N+1; i<l; i++)
	    		graph.add(null);
	    	next = 0;  
	    	dfs_low = new int[N+1]; dfs_parent = new int[N+1]; done = new boolean[N+1]; dfs_num = new int[N+1];
	    	Arrays.fill(dfs_low, -1); Arrays.fill(dfs_parent, -1); 
	    	Arrays.fill(done, false); Arrays.fill(dfs_num, -1);
	    	while(true)
	    	{
		    	String line = MyScanner.readLine();
		    	StringTokenizer tokens = new StringTokenizer(line);
		    	int from = MyScanner.parseInt(tokens.nextToken());
	    		if(from == 0) break;
	    		int to;
	    		while(tokens.hasMoreTokens())
	    		{
	    			to = MyScanner.parseInt(tokens.nextToken());
		    		addChildren(from, to); //construct a two-way mapping
		    		addChildren(to, from);
	    		}
	    	}
	    	for(int i=0; i<=N; i++)
	    		if(done[i] == false)
    			{
	    			root = i; rootChildCount = 0;
    				dfs(i); //check articulation points
    				if(rootChildCount == 1) articulationPoints.remove(i);
    			}
    		pw.println(articulationPoints.size());
	    	graph.clear(); articulationPoints.clear(); //clear
    	}
    	pw.flush();
    	pw.close();
    	MyScanner.close();
	}
    
    
    /**
     * Create a bi-directional graph
     * @param from
     * @param to
     */
    private static void addChildren(int from, int to)
    {
		Set<Integer> children = graph.get(from);
		if(children == null)
			children = new HashSet<>();
		children.add(to);
		graph.set(from, children);
    }
    
    /**
     * Dfs count articulation points
     * @return int
     */
    private static int dfs(int i)
    {
    	int curVal = next++;
    	dfs_low[i] = curVal; dfs_num[i] = curVal; 
		done[i] = true; //mark visited
    	Set<Integer> children = graph.get(i);
    	if(children == null) return dfs_low[i];
    	for(int child : children)
    	{
    		if(done[child] == false) //unvisited
    		{
    			dfs_parent[child] = i;
    			int min = dfs(child);
    			if(min >= dfs_num[i]) articulationPoints.add(i); //add the articulation point to the set
    			dfs_low[i] = Math.min(min, dfs_low[i]);
    		}
    		else
    		{
    			//check for back-edge
    			int parent = dfs_parent[i];
    			if(parent == child) 
    			{
        			if(parent == root) rootChildCount ++; //special case check for root node
        			continue; //if child is parent
    			}
    			else dfs_low[i] = Math.min(dfs_num[child], dfs_low[i]); //back-edge (could be also forward edge)
    		}
    	}
    	return dfs_low[i];
    }
}
