package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.062 s. Simple topological sort using recursive DFS approach.
 *
 */
public class OrderingTasks {

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
    
    private static List<List<Integer>> graph = new ArrayList<>(101);
    private static final String BLANK = " ";
    private static int N, M, pos; //test case number and number of vertices
    private static boolean[] ZO = new boolean[102], DONE = new boolean[102]; 
    private static int[] toposort = new int[102];
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
    		M = MyScanner.readInt();
    		for(int i=0; i<=N; i++)
    			graph.add(null);
    		Arrays.fill(ZO, true); Arrays.fill(DONE, false); //initialize with values
    		pos = 0; // reset position
    		for(int i=0; i<M; i++)
    		{
    			int from = MyScanner.readInt();
    			int to = MyScanner.readInt();
    			List<Integer> children = graph.get(from);
    			if(children == null) children = new ArrayList<>(N);
    			children.add(to); graph.set(from, children);
    			ZO[to] = false; //mark this as a non-zero order vertex
    		}
    		for(int i=1; i<=N; i++)
    			if(ZO[i] == true) dfs(i);
    		pw.print(toposort[N-1]);
    		for(int i=N-2; i>=0; i--)
    			pw.print(BLANK + toposort[i]);
    		pw.println();
    		graph.clear(); //clean up
    	}
    	pw.flush();
    	pw.close();
    	MyScanner.close();
	}

    /**
     * Perform DFS to print the topological order of vertices 
     * @param i
     */
    private static void dfs(int i)
    {
    	DONE[i] = true;
    	List<Integer> children = graph.get(i);
    	if(children != null)
    	for(int c=0, l = children.size(); c<l; c++)
    		if(DONE[children.get(c)] == false) dfs(children.get(c));
    	toposort[pos++] = i;
    }
}
