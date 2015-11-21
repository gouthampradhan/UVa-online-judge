package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.295 s Java toper! Interesting problem find articulation points + count the connected components
 *
 */
public class DovesAndBombs {

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
                return -2;
            }
            return -2;
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
    private static int N, M, root, next, rootChildCount;
    private static int[] dfs_low, dfs_num, dfs_parent; 
    private static boolean[] done, bombed;
    private static final String BLANK = " ";
    private static List<Station> articulationPoints = new ArrayList<>();
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * 
     * @author gouthamvidyapradhan
     * Station comparator
     * Sort based on descending order of pigeon value, if pigeon value is same then sort based on ascending order of index
     *
     */
    static class StationComparator implements Comparator<Station>
    {
		@Override
		public int compare(Station s1, Station s2) 
		{
			return (s1.pigeonV > s2.pigeonV)? -1 : (s2.pigeonV > s1.pigeonV)? 1 : (s1.index < s2.index) ? -1 : 1;
		}
    }
    /**
     * 
     * @author gouthamvidyapradhan
     *
     */
    static class Station
    {
    	int index;
    	int pigeonV;
    	Station(int index, int pigeonV)
    	{
    		this.index = index;
    		this.pigeonV = pigeonV;
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
	    	while((N = MyScanner.readInt()) == -2);
	    	M = MyScanner.readInt();
	    	if(N == 0 && M == 0) break;
    		graph = new ArrayList<>(N); articulationPoints = new ArrayList<>(N);
	    	next = 0;  
	    	dfs_low = new int[N]; dfs_parent = new int[N]; done = new boolean[N]; dfs_num = new int[N]; bombed = new boolean[N];
	    	for(int i=0; i<N; i++)
	    		{dfs_low[i] = -1; dfs_parent[i] = -1; dfs_num[i] = -1; done[i] = false; bombed[i] = false; graph.add(i, null);}
	    	while(true)
	    	{
		    	String line = MyScanner.readLine();
		    	StringTokenizer tokens = new StringTokenizer(line);
		    	int from = MyScanner.parseInt(tokens.nextToken());
		    	int to = MyScanner.parseInt(tokens.nextToken());
	    		if(from == -1) break;
	    		addChildren(from, to); //construct a two-way mapping
	    		addChildren(to, from);
	    	}
	    	root = 0; rootChildCount = 0;
	    	dfs(0); //check articulation points starting from root vertex 0
	    	int last = articulationPoints.size() - 1;
	    	if(rootChildCount == 1)  articulationPoints.remove(last); //remove the last added station (last added station should be the root)
	    	else if(last >= 0) articulationPoints.get(last).pigeonV--; //the count of connected components of root is always one higher than expected
	    	Collections.sort(articulationPoints, new StationComparator());
	    	int i=0;
	    	for(int l = articulationPoints.size(); i<M && i<l; i++) //print sorted articulation points
	    	{
	    		Station s = articulationPoints.get(i);
	    		pw.println(s.index + BLANK + s.pigeonV);
	    		bombed[s.index] = true;
	    	}
	    	for(int j=0, l = bombed.length; i<M && j<l; j++) //print remaining vertices
	    		if(!bombed[j]) { pw.println(j + BLANK + 1); i++; }
	    	pw.println();
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
    	Station s = new Station(i, 1);
    	for(int child : children)
    	{
    		if(done[child] == false) //unvisited
    		{
    			dfs_parent[child] = i;
    			int min = dfs(child);
    			if(min >= dfs_num[i])
    				s.pigeonV++; //increase the pigeon value
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
    	if(s.pigeonV > 1) articulationPoints.add(s); //pigeon value is greater than one so add this as articulation point
    	return dfs_low[i];
    }
}
