package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted in first attempt 1.962 s. Definitely needs improvement in the algorithm applied
 * Accepted third attempt 0.179 s !!! A definite improvement in the algorithm. Use of simple arrays and primitive types. 
 * Followed an approach of a C++ solution found online - Graph is constructed as a binary tree. 
 *
 */
public class MonitoringTheAmazon 
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
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return parseInt(st.nextToken());
                }
            } catch (IOException e) {
                close();
                return Integer.MAX_VALUE;
            }
            return Integer.MAX_VALUE;
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

    private static final String REACHABLE = "All stations are reachable.";
    private static final String UNREACHABLE = "There are stations that are unreachable.";
    private static int[] X;
    private static int[] Y;
    private static int N;
    private static int[][] graph;
    private static boolean[] visited;

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            while((N = MyScanner.readInt()) == Integer.MAX_VALUE);
            if(N == 0) break;
            if(N == 1 || N == 2 || N == 3)
            {
                pw.println(REACHABLE);
                MyScanner.readLine(); //ignore the next line
                continue;
            }
            X = new int[N]; Y = new int[N];
            graph = new int[N][2];
            visited = new boolean[N];
            for(int i=0; i<N; i++)
            {
                X[i] = MyScanner.readInt();
                Y[i] = MyScanner.readInt();
            }
            buildGraph(); //construct graph
            if(dfs(0) == N)
                pw.println(REACHABLE);
            else
                pw.println(UNREACHABLE);
        }
        pw.flush();
        pw.close();
        MyScanner.close();
    }

    /**
     * Count nodes
     * @param i int start vertex
     * @return total count
     */
    private static int dfs(int i)
    {
        if(visited[i])
            return 0;
        visited[i] = true;
        return dfs(graph[i][0]) + dfs(graph[i][1]) + 1;
    }

    /**
     *
     */
    private static void buildGraph()
    {
        for(int i=0; i<N; i++)
        {
            int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
            int c1 = -1, c2 = -1;
            for(int j=0; j<N; j++)
            {
            	if (i != j) 
            	{
	                int x1 = X[i]; int y1 = Y[i];
	                int x2 = X[j]; int y2 = Y[j];
	                int distance = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
	                if(checkMin(distance, min1, x2, y2, c1))
	                {
	                    min2 = min1;
	                    min1 = distance;
	                    c2 = c1;
	                    c1 = j;
	                }
	                else if(checkMin(distance, min2, x2, y2, c2))
	                {
	                    min2 = distance;
	                    c2 = j;
	                }
            	}
            }
            graph[i][0] = c1;
            graph[i][1] = c2;
        }
    }
    
    /**
     * Method to check for new minimum
     * @param distance
     * @param min2
     * @param x2
     * @param y2
     * @param c2
     * @return true if a new minimum is found, false otherwise
     */
    private static boolean checkMin(int distance, int min, int x, int y, int c)
    {
        if(distance < min)
        	return true;
        else if(distance == min)
        {
            if((x < X[c]) || (x == X[c] && y < Y[c]))
               return true;
        }
        return false;
    }

}
