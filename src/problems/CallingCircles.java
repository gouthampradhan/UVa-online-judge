package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted : 0.249 Simple problem of printing SCC using Trajan's algorithm
 *
 */
public class CallingCircles 
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
    private static int N, M, next, index, top, caseCount;
    private static int[] dfs_low, dfs_num, stack; 
    private static boolean[] done, visited;
    private static final String COMMA_BLANK = ", ", CALLING_CIRCLE = "Calling circles for data set ", COLON = ":";
    private static Map<String, Integer> nameId = new HashMap<>();
    private static Map<Integer, String> idName = new HashMap<>();
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
	{
    	caseCount = 0; 
    	while(true)
    	{
	    	while((N = MyScanner.readInt()) == -1);
	    	M = MyScanner.readInt();
	    	if(N == 0 && M == 0) break;
    		graph = new ArrayList<>(N);
	    	next = 0; index = 0; top = 0; 
	    	dfs_low = new int[N]; stack = new int[N]; done = new boolean[N]; dfs_num = new int[N]; visited = new boolean[N];
	    	for(int i=0; i<N; i++)
	    		{dfs_low[i] = -1; stack[i] = -1; dfs_num[i] = -1; done[i] = false; visited[i] = false; graph.add(i, null);}
	    	while(M-- > 0)
	    	{
		    	String line = MyScanner.readLine();
		    	StringTokenizer tokens = new StringTokenizer(line);
		    	String from = tokens.nextToken();
		    	String to = tokens.nextToken();
		    	int fromId, toId;
		    	if(nameId.get(from) == null)
		    	{
		    		fromId = index++;
		    		nameId.put(from, fromId);
		    		idName.put(fromId, from);
		    	}
		    	if(nameId.get(to) == null)
		    	{
		    		toId = index++;
		    		nameId.put(to, toId);
		    		idName.put(toId, to);
		    	}
		    	addChildren(nameId.get(from), nameId.get(to));
	    	}
	    	if(caseCount != 0)
	    		pw.println();
	    	pw.println(CALLING_CIRCLE + ++caseCount + COLON);
	    	for(int i=0; i<N; i++)
	    		if(!done[i] && (graph.get(i) != null)) dfs(i); //check for SCC
	    	nameId.clear(); idName.clear();
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
    private static void dfs(int i)
    {
    	int curVal = next++;
    	dfs_low[i] = curVal; dfs_num[i] = curVal; 
		done[i] = true; //mark finished
		visited[i] = true; //mark visited (vertex currently being explored)
		stack[top++] = i; //push to stack
    	Set<Integer> children = graph.get(i);
    	if(children != null)
    	for(int child : children)
    	{
    		if(!done[child]) //unvisited
    			dfs(child);
    		if(visited[child])
    			dfs_low[i] = Math.min(dfs_low[child], dfs_low[i]);
    	}
    	if(dfs_low[i] == dfs_num[i]) //root
    	{
    		while(true)
    		{
        		int t = stack[--top];
        		pw.print(idName.get(t));
        		visited[t] = false;
        		if(t == i) {pw.println(); break;} //root
    			pw.print(COMMA_BLANK);
    		}
    	}
    }
}
