package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.109 s. Simple variant of Floyd-Warshall's algorithm. 
 *
 */
public class Arbitrage {

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
    private static Map<String, Integer> currencyIndex = new HashMap<String, Integer>();
    private static double[][] graph;
    private static int N, M;
    private static final String CASE = "Case ", COLON = ": ", YES = "Yes", NO = "No";

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        int count = 0;
        while(true)
        {
        	while((N = MyScanner.readInt()) == -1);
        	if(N == 0) break;
        	graph = new double[N][N];
            for(int i = 0; i < N; i++)
            {
            	currencyIndex.put(MyScanner.readLine().trim(), i);
            	graph[i][i] = 1.0;
            }
            while((M = MyScanner.readInt()) == -1);
            for(int i = 0; i < M; i++)
            {
            	String line = MyScanner.readLine();
            	StringTokenizer st = new StringTokenizer(line);
            	int from = currencyIndex.get(st.nextToken());
            	double cost = Double.parseDouble(st.nextToken());
            	int to = currencyIndex.get(st.nextToken());
            	graph[from][to] = cost;
            }
            boolean arbitrage = apsp();
        	pw.print(CASE + ++count + COLON);
        	if(arbitrage)
        		pw.println(YES);
        	else
        		pw.println(NO);
        	currencyIndex.clear();
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Floyd-Warshall's all pair shortest path algorithm
     */
    private static boolean apsp()
    {
        for(int w = 0; w < N; w++)
        {  
            for(int u = 0; u < N; u++) 
            {
                for (int v = 0; v < N; v++)
                {
                	if((graph[u][w] == 0.0) || graph[w][v] == 0.0) continue; //no alternate route available.
                	graph[u][v] = Math.max(graph[u][v], (graph[u][w] * graph[w][v]));
                	if(u == v && graph[u][v] > 1.0)
                		return true; //stop as soon as a profit is spotted.
                }
            }
        }
        return false;
    }

}
