package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.049 s. Simple Floyd-Warshall's algorithm of finding transitive closure.
 *
 */
public class IdentifyingConcurrentEvents {

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
    
    private static class Event
    {
    	int e1;
    	int e2;
    	Event(int e1, int e2)
    	{
    		this.e1 = e1;
    		this.e2 = e2;
    	}
    }
    
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static Map<String, Integer> eventIndex = new HashMap<String, Integer>();
    private static List<String> reverseIndex = new ArrayList<>();
    private static List<Event> events = new ArrayList<>();
    private static int[][] graph;
    private static int N, NM, CE;
    private static final String CASE = "Case ", CONCURRENT_EVENTS = "concurrent events", BLANK = " ", 
    		NO = "no ", COLON = ":", STOP = ".", COMMA = ",", OPEN = "(", CLOSE = ")";

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
        	int index = 0; CE = 0;
        	String prev = null;
            for(int i = 0; i < N; i++)
            {
            	String line = MyScanner.readLine();
            	StringTokenizer st = new StringTokenizer(line);
            	st.nextToken(); //ignore the first token
            	while(st.hasMoreTokens())
            	{
            		String current = st.nextToken();
            		if(eventIndex.get(current) == null)
            		{
            			eventIndex.put(current, index);
            			reverseIndex.add(index++, current); //store the reverse index
            		}
            		if(prev != null)
            			events.add(new Event(eventIndex.get(prev), eventIndex.get(current)));
            		prev = current; //mark current as prev
            	}
            	prev = null;
            }
            NM = index; //number of events
            graph = new int[NM][NM];
            int messages = MyScanner.readInt();
            for(int i = 0; i < messages; i++) //read messages
            {
            	String line = MyScanner.readLine();
            	StringTokenizer st = new StringTokenizer(line);
            	graph[eventIndex.get(st.nextToken())][eventIndex.get(st.nextToken())] = 1 ;	
            }
            for(Event e : events)
            	graph[e.e1][e.e2] = 1;
            apsp();
            events.clear();
            for(int i = 0; i<NM; i++)
            {
            	for(int j = 0; j<NM; j++)
            	{
            		if(i == j) continue;
            		if(graph[i][j] == 0 && graph[j][i] == 0) //no font or back-edge
            		{
            			graph[j][i] = 1; //mark this as 1 so that this is not recounted in the next iterations
            			CE++; //count concurrent events
            			if(CE <= 2)
            				events.add(new Event(i, j));
            		}
            	}
            }
            pw.print(CASE + ++count + "," + BLANK);
            if(CE == 0)
            	pw.print(NO + CONCURRENT_EVENTS + STOP);
            else
            {
            	pw.println(CE + BLANK + CONCURRENT_EVENTS + COLON);
            	for(int i = 0, l = events.size(); i < l; i++)
        			pw.print(OPEN + reverseIndex.get(events.get(i).e1) + COMMA + reverseIndex.get(events.get(i).e2) + CLOSE + BLANK);
            }
            pw.println();
            eventIndex.clear(); reverseIndex.clear(); events.clear();
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Floyd-Warshall's all pair shortest path algorithm
     */
    private static boolean apsp()
    {
        for(int w = 0; w < NM; w++)
        {  
            for(int u = 0; u < NM; u++) 
            {
                for (int v = 0; v < NM; v++)
                	graph[u][v] |= (graph[u][w] & graph[w][v]); //transitive closure
            }
        }
        return false;
    }
}
