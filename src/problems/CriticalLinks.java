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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.182 s. Simple problem of finding bridges using Trajan's algorithm
 *
 */
public class CriticalLinks {

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
                if (!str.trim().equals("")) {
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
    
    /**
     * Class to store the link
     * @author gouthamvidyapradhan
     *
     */
    static class Link
    {
    	Link(int from, int to)
    	{
    		this.from = from;
    		this.to = to;
    	}
    	int from;
    	int to;
    }
    
    /**
     * 
     * @author gouthamvidyapradhan
     * Comparator to sort the critical links list
     *
     */
    static class LinkComparator implements Comparator<Link>
    {
		@Override
		public int compare(Link l1, Link l2) 
		{
			return (l1.from < l2.from) ? -1 : (l1.from > l2.from) ? 1 : (l1.to < l2.to)? -1 : (l1.to > l2.to)? 1 : 0;
		}
    }
    private static List<Set<Integer>> graph;
    private static int N, next;
    private static int[] dfs_low, dfs_num, dfs_parent; 
    private static boolean[] done;
    private static List<Link> criticalLinks;
    private static final String CRITICAL_LINKS = "critical links", BLANK = " ", BLANK_DASH = " - ";
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
	    	if(N == -2) break; //eof
    		graph = new ArrayList<>(N); criticalLinks = new ArrayList<>(N);
	    	for(int i=0, l = N; i<l; i++) {graph.add(null);}
	    	next = 0;  
	    	dfs_low = new int[N]; dfs_parent = new int[N]; done = new boolean[N]; dfs_num = new int[N];
	    	Arrays.fill(dfs_low, -1); Arrays.fill(dfs_parent, -1); 
	    	Arrays.fill(done, false); Arrays.fill(dfs_num, -1);
	    	for(int i=0; i<N; i++)
	    	{
		    	String line = MyScanner.readLine();
		    	StringTokenizer tokens = new StringTokenizer(line);
		    	int from = MyScanner.parseInt(tokens.nextToken());
	    		int to;
	    		tokens.nextToken(); //ignore this token example (0), (19) etc. . 
	    		while(tokens.hasMoreTokens())
	    		{
	    			to = MyScanner.parseInt(tokens.nextToken());
		    		addChildren(from, to); //construct a two-way mapping
		    		//addChildren(to, from);
	    		}
	    	}
	    	for(int i=0; i<N; i++)
	    		if(!done[i])
    				dfs(i); //check bridges
	    	Collections.sort(criticalLinks, new LinkComparator());
	    	pw.println(criticalLinks.size() + BLANK + CRITICAL_LINKS); 
	    	for(Link l : criticalLinks)
	    		pw.println(l.from + BLANK_DASH + l.to);
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
     * Dfs to check for bridges
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
    		if(done[child]) //unvisited
    		{
    			//check for back-edge
    			int parent = dfs_parent[i];
    			if(parent != child) 
    				dfs_low[i] = Math.min(dfs_num[child], dfs_low[i]); //back-edge (could be also forward edge)
    		}
    		else
    		{
    			dfs_parent[child] = i;
    			int min = dfs(child);
    			if(min > dfs_num[i])
    				criticalLinks.add((i < child) ? new Link(i, child) : new Link(child, i)); //bridge is found, add to criticalLinks
    			dfs_low[i] = Math.min(min, dfs_low[i]);
    		}
    	}
    	return dfs_low[i];
    }
}
