package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.186 s. Simple Floyd-Warshall's algorithm.
 *
 */
public class Commandos {
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
    private static int[][] graph;
    private static int[] time;
    private static int T, R, u1, v1, s, f, N, MAX;
    private static final String CASE = "Case ", COLON = ": ";

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        int count = 0;
        T = MyScanner.readInt();
        while(T-- > 0)
        {
        	MAX = Integer.MIN_VALUE;
        	while((N = MyScanner.readInt()) == -1);
        	graph = new int[N][N];
        	time = new int[N];
        	while((R = MyScanner.readInt()) == -1);
        	while(R-- > 0)
        	{
            	while((u1 = MyScanner.readInt()) == -1);
                v1 = MyScanner.readInt();
                graph[u1][v1] = 1;
                graph[v1][u1] = 1; //make two way connection
        	}
        	while((s = MyScanner.readInt()) == -1); //start 
        	f = MyScanner.readInt(); //finish
        	apsp();
            for(int i = 0; i < N; i++)
            	time[i] = graph[s][i];
            for(int i = 0; i < N; i++)
            {
            	//if(i == s) continue; //find max to all except source
            	time[i] += graph[i][f];
            	MAX = Math.max(MAX, time[i]);
            }
            pw.println(CASE + ++count + COLON + ((MAX == Integer.MIN_VALUE) ? 0 : MAX)); 
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Floyd-Warshall's all pair shortest path algorithm
     */
    private static void apsp()
    {
        for(int w = 0; w < N; w++)
        {  
            for(int u = 0; u < N; u++) 
            {
                for (int v = 0; v < N; v++)
                {
                	if(u == v) continue; 
                	else if((graph[u][w] == 0) || graph[w][v] == 0) continue; //no alternate route available.
                	else if(graph[u][v] == 0) graph[u][v] = graph[u][w] + graph[w][v]; //alternate route is the best route.
                    else graph[u][v] = Math.min(graph[u][v], graph[u][w] + graph[w][v]);
                }
            }
        }
    }
}
