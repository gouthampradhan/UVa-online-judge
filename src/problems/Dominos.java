package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.455 s after a big struggle. Received a RTE 4 times possibly because of StackOverflowError due to deep recursion. 
 * I have still not figured out why a class Vertex containing adjlist gets AC but a List<List<Integer>> fails
 *
 */
public class Dominos {

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

    /**
     * 
     * @author gouthamvidyapradhan
     *
     */
    static class Vertex 
    {
		List<Integer> adj;
		public Vertex() {
			adj = new ArrayList<Integer>();
		}
	}
    
    private static Vertex[] graph;
    private static int T, N, M, top, count;
    private static int[] stack;
    private static boolean[] visited;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        while((T = MyScanner.readInt()) == -1);
        while(T-- > 0)
        {
            while((N = MyScanner.readInt()) == -1);
            M = MyScanner.readInt();
            top = 0; count = 0;
            visited = new boolean[N + 1];
            graph = new Vertex[N + 1]; stack = new int[N + 1];
            for(int i=0; i<=N; i++)
            {graph[i] = new Vertex();}
            while(M-- > 0)
                graph[MyScanner.readInt()].adj.add(MyScanner.readInt());
            for(int i=1; i<=N; i++)
                if(!visited[i])
                	dfs(i); //toposort
            visited = new boolean[N + 1];
            while(--top >= 0)
            	if(!visited[stack[top]])
            	{dfs2(stack[top]); count++;}
            pw.println(count);
        }
        pw.flush();
        pw.close();
        MyScanner.close();
    }

    /**
     * Dfs to perform a toposort
     * @return int
     */
    private static void dfs(int i)
    {
    	visited[i] = true;
    	for(int c : graph[i].adj)
    		if(!visited[c]) 
    			dfs(c);
    	stack[top++] = i; //push to stack
    }
    
    /**
     * Simple dfs and mark vertices as visited
     * @return int
     */
    private static void dfs2(int i)
    {
    	visited[i] = true;
    	for(int c : graph[i].adj)
    		if(!visited[c]) 
    			dfs2(c);
    }
}
