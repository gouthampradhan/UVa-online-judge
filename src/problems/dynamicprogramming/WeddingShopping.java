package problems.dynamicprogramming;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.060. Simple DP solution. Similar to complete search using backtracking
 *
 */
public class WeddingShopping {

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
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static long readLong() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return Long.parseLong(st.nextToken());
                }
                String str = br.readLine();
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return Long.parseLong(st.nextToken());
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
    private static int N, M, C, K, total;
    private static int[][] T, P; 
    private static final String NO_SOLUTION = "no solution";

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		N = MyScanner.readInt();
		while(N-- > 0)
		{
			M = MyScanner.readInt();
			C = MyScanner.readInt();
			P = new int[C][];
			T = new int[C][M + 1];
			total = Integer.MIN_VALUE;
			for(int i = 0; i < C; i++)
			{
				K = MyScanner.readInt();
				P[i] = new int[K + 1];
				P[i][0] = K;
				for(int j = 1; j <= K; j++)
					P[i][j] = MyScanner.readInt();
				for(int m = 0; m <= M; m++)
					T[i][m] = -1;
			}
			total = dp(0, M);
			if(total == 0) pw.println(NO_SOLUTION);
			else pw.println(total);
				
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Dp to get the maximum 
	 * @param c
	 * @param m
	 * @return
	 */
	private static int dp(int c, int m)
	{
		if(c == C)
		{
			if(m < 0) return 0;
			else return M - m;
		}
		if(T[c][m] >= 0) return T[c][m]; //this is the only difference between complete search backtracking and DP
		int max = Integer.MIN_VALUE;
		for(int i = 1, k = P[c][0]; i <= k; i++)
		{
			int item = P[c][i];
			if((m - item) >= 0)
				max = Math.max(dp(c + 1, m - item), max);
		}
		T[c][m] = (max == Integer.MIN_VALUE) ? 0 : max; //save the result
		return T[c][m]; 
	}
}
