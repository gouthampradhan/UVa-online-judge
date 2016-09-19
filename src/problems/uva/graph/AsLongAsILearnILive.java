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
 * Accepted first attempt 0.276 s !
 * Simple DFS search, no cycles in the graph hence the graph is simple and can be solved using just primitive types.
 *
 */
public class AsLongAsILearnILive {

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

    private static final String CASE = "Case ";
    private static final String COLON = ":";
    private static final String BLANK = " ";
    private static int[] P; //points
    private static int N, M;
    private static int[] graph;
    private static int finish;

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception
	{
		int T, count = 1;
        while((T = MyScanner.readInt()) == -1);
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			M = MyScanner.readInt();
			graph = new int[N]; P = new int[N];
			for(int i = 0; i < N; i++)
				P[i] = MyScanner.readInt(); //read all the points for each node
			for(int i = 0; i < M; i++)
			{
				int f = MyScanner.readInt(); //from
				int t = MyScanner.readInt(); //to
				if(P[t] > P[graph[f]])
					graph[f] = t; //maintain the connection to node with max points
			}
			pw.println(CASE + count + COLON + BLANK + dfs(0) + BLANK + finish);
			count++;
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * DFS to reach the final destination and collect points
	 * @param i start vertex
	 * @return total points
	 */
	private static int dfs(int i)
	{
		if(graph[i] == 0){finish = i; return P[i];} //final destination reached.
		return dfs(graph[i]) + P[i];
	}
}
