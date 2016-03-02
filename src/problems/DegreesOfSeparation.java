package problems;

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
 * Accepted 0.096s. Fastest in Java, simple Floyd-Warshall's algorithm. CAUTION : Input can be tricky with lot of spaces and next line characters.
 *
 */
public class DegreesOfSeparation {

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
    private static Map<String, Integer> nameIndex = new HashMap<String, Integer>();
    private static int[][] graph;
    private static int P, R;
    private static final String CASE = "Network ", DISCONNECTED = "DISCONNECTED", COLON = ": ";

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        int count = 0;
        while(true)
        {
        	while((P = MyScanner.readInt()) == -1);
        	if(P == 0) break;
        	R = MyScanner.readInt();
        	graph = new int[P + 5][P + 5];
        	int index = 0;
        	int countRelations = 0;
        	String prev = null;
        	while(countRelations < R)
        	{
            	StringTokenizer st = new StringTokenizer(MyScanner.readLine());
            	while(st.hasMoreTokens())
            	{
            		if(prev == null)
            			prev = st.nextToken().trim();
            		else
            		{
                		String curr = st.nextToken().trim();
                		if(nameIndex.get(prev) == null)
                			nameIndex.put(prev, index++);
                		if(nameIndex.get(curr) == null)
                			nameIndex.put(curr, index++);
                		graph[nameIndex.get(prev)][nameIndex.get(curr)] = 1;
                		graph[nameIndex.get(curr)][nameIndex.get(prev)] = 1; //make two way connection
                		countRelations++;
                		prev = null;
            		}
            	}
        	}
        	apsp();
        	int max = getMax();
        	pw.print(CASE + ++count + COLON);
        	if(max == -1)
        		pw.println(DISCONNECTED);
        	else
        		pw.println(max);
        	pw.println();
            nameIndex.clear();
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Method to get max degree
     * @return
     */
    private static int getMax()
    {
    	int max = -1;
    	for(int i = 0; i < P; i++)
    	{
    		for(int j = 0; j < P; j++)
    		{
    			if(i == j) continue;
    			if(graph[i][j] == 0) //if the graph is disconnected
    				return -1;
    			max = Math.max(max, graph[i][j]);
    		}
    	}
    	return max;
    }
    
    /**
     * Floyd-Warshall's all pair shortest path algorithm
     */
    private static void apsp()
    {
        for(int w = 0; w < P; w++)
        {  
            for(int u = 0; u < P; u++) 
            {
                for (int v = 0; v < P; v++)
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
