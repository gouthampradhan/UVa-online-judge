package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.352s. Simple Floyd-Warshall's algorithm. 
 * Accepted 0.252s : Some simple optimization while printing and avoiding type casting in O(NX3) loop.
 *
 */
public class PageHopping {

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
    private static int u1, v1, u2, v2, N, u, v, w;
    private static double sum;
    private static Set<Integer> nodeSet = new HashSet<>(); //maintain a set of unique nodes
    private static int[] nodes;
    private static final String CASE = "Case ";
    private static final String AVERAGE_LENGTH = ": average length between pages = ";
    private static final String CLICKS = " clicks";
    private static final DecimalFormat format = new DecimalFormat("0.000");

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        int count = 0;
        while(true)
        {
        	while((u1 = MyScanner.readInt()) == -1);
            v1 = MyScanner.readInt();
            if(u1 == 0) break;
            graph = new int[101][101];
            graph[u1][v1] = 1;
            nodeSet.add(u1); nodeSet.add(v1);
            while(true)
            {
            	while((u2 = MyScanner.readInt()) == -1);
                v2 = MyScanner.readInt();
                if(u2 == 0) break;
                graph[u2][v2] = 1;
                nodeSet.add(u2); nodeSet.add(v2);
            }
            Object[] temp = nodeSet.toArray();
            N = temp.length;
            nodes = new int[N];
            for(int i = 0; i < N; i++)
            	nodes[i] = (int)temp[i]; //copy to simple array
            apsp(); //invoke all pair shortest path
            sum = 0; int edges = N * (N - 1);
            for(int i = 0; i < N; i++)
            	for(int j = 0; j < N; j++)
            			sum += graph[nodes[i]][nodes[j]];
            pw.println(CASE + ++count + AVERAGE_LENGTH + format.format(sum / edges) + CLICKS);
            nodeSet.clear();
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Floyd-Warshall's all pair shortest path algorithm
     */
    private static void apsp()
    {
        for(int i = 0; i < N; i++)
        {  
        	w = nodes[i];
            for(int j = 0; j < N; j++) 
            {
            	u = nodes[j];
                for (int k = 0; k < N; k++)
                {
                	v = nodes[k];
                	if(u == v) continue; 
                	else if((graph[u][w] == 0) || graph[w][v] == 0) continue; //no alternate route available.
                	else if(graph[u][v] == 0) graph[u][v] = graph[u][w] + graph[w][v]; //alternate route is the best route.
                    else graph[u][v] = Math.min(graph[u][v], graph[u][w] + graph[w][v]);
                }
            }
        }
    }
}