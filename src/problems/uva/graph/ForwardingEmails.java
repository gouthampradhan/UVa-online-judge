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
 * Accepted 0.532 s. Definitely needs improvement in algorithm
 *
 */
public class ForwardingEmails {
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
    private static int N;
    private static int[] graph = new int[50001];
    private static int[] total = new int[50001];
    private static boolean[] visited = new boolean[50001];

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception
	{
		int T, count = 1, M, max; //test, count, martian and max
        while((T = MyScanner.readInt()) == -1);
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			M = 0; max = 0;
			for(int i=1; i<=N; i++)
			{
				int f;
				while((f = MyScanner.readInt()) == -1);
				graph[f] = MyScanner.readInt(); 
				visited[i] = false;
				total[i] = -1;
			}
			for(int i=1; i<=N; i++)
			{
				if(total[i] == -1)
				{
					int temp = dfs(i);
					if(temp > max)
					{
						max = temp; //maintain a new max value and martian
						M = i;
					}
				}
			}
			pw.println(CASE + count + COLON + BLANK + M);
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
		visited[i] = true;
		int count = 0;
		if(visited[graph[i]] == false)
			count = 1 + dfs(graph[i]);
		visited[i] = false;
		total[i] = count;
		return count;
	}
}
