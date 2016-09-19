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
 * Accepted 0.132s. Fastest in java. Simple problem, little bit tricky to convert a simple string to different states.
 *
 */
public class AvoidingJungleInTheDark {

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
    private static int[] min;
    private static int T, result, MAXIMUM;
    private static char[] LAND;
    private static boolean[] visited;
    private static final char D = 'D', J = '*';
    private static final int[] DELAY = {0, 8, 16};
    private static final String CASE = "Case #", COLON = ": ";

    /**
     * Main method
     * @param args
     */

	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		int count = 0;
		while(T-- > 0)
		{
			LAND = MyScanner.readLine().toCharArray();
			min = new int[1024536]; visited = new boolean[1024536];
			result = Integer.MAX_VALUE; MAXIMUM = Integer.MAX_VALUE;
			int result = dp(0, 16, 6, 0); //index, hours left, current time, total count of hours
			pw.print(CASE + ++count + COLON);
			if(result == MAXIMUM)
				pw.println(-1);
			else
				pw.println(result);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Dp to find the min distance
	 * @param i Land index
	 * @param k Total hours left
	 * @param h Current time in hours
	 * @param count total count
	 * @return minimum hours required to reach destination.
	 */
	private static int dp(int i, int k, int h, int count)
	{
		if(k < 0) return MAXIMUM;
		char curr = LAND[i];
		if(curr == D) 
		{
			result = Math.min(count, result);
			return result;
		}
		if(curr == J && (h >= 18 || h <= 6)) return MAXIMUM; //not allowed in jungle in dark hours 
		int encode = (((i << 5) + k) << 5) + h;
		if(visited[encode]) return min[encode];
		visited[encode] = true; //mark this as visited
		min[encode] = MAXIMUM;
		if(curr == J)
			min[encode] = Math.min(min[encode], dp(i+1, k - 1, (h + 1) % 24, count+1)); //only forward journey allowed. Not allowed to sleep
		else
			for(int j = 0; j < 3; j++)
				min[encode] = Math.min(min[encode], dp(i+1, (j==0)? k - 1 : 15, (h + DELAY[j] + 1) % 24, 
						(count + 1 + DELAY[j])));//forward journey and sleep allowed
		return min[encode];
	}
}
