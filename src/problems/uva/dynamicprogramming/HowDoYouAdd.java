package problems.uva.dynamicprogramming;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.280. Simple DP similar to WeddingShopping
 * Worst case algorithm complexity O(N ^ 3)
 *
 */
public class HowDoYouAdd {

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
    private static int N, K;
    private static int[][] M; 

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args)  throws Exception 
	{
		while(true)
		{
			N = MyScanner.readInt();
			K = MyScanner.readInt();
			if(N == 0) break;
			M = new int[K][N + 1];
			for(int i = 0; i < K; i++)
			{
				for(int j = 0; j <= N; j++)
					M[i][j] = -1;
			}
			pw.println(dp(0, 0));
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Dp to get the maximum 
	 * @param c
	 * @param m
	 * @return
	 */
	private static int dp(int k, int sum)
	{
		if(k == K)
		{
			if(sum == N) return 1;
			else return 0;
		}
		if(M[k][sum] >= 0) return M[k][sum]; //this is the only difference between complete search backtracking and DP
		int total = 0;
		for(int i = 0; i <= N; i++)
		{
			int t = sum + i;
			if(t <= N)
				total = (total + dp(k + 1, t)) % 1000000;
			else break;
		}
		M[k][sum] = total;
		return M[k][sum]; 
	}
}