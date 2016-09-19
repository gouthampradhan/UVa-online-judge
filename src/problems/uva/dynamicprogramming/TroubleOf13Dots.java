package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 09/06/2016.
 * Accepted 0.570. Relatively simple knapsack DP problem, but really struggled to think about the algorithim and implementation because of tricky twist in the
 * algotithm.
 * Algorithm compelxity : O(N x M)
 */
public class TroubleOf13Dots
{
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
    private static int M1, M2, N, max;
    private static int[] P, F;
    private static int[][] DP;

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            while((M1 = MyScanner.readInt()) == -1);
            if(M1 == -2) break;
            N = MyScanner.readInt();
            P = new int[N]; F = new int[N];
            for(int i = 0; i < N; i ++)
            {
                P[i] = MyScanner.readInt();
                F[i] = MyScanner.readInt();
            }
            DP = new int[N + 1][M1 + 201];
            for(int i = 0; i <= N; i ++)
                Arrays.fill(DP[i], -1);
            max = 0;
            if(M1 > 1800)
                M2 = M1 + 200;
            else M2 = M1;
            dp(0, 0);
            pw.println(max);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Dp to find the max favour value
     * @param i index
     * @param m total money spent
     * @return max
     */
    private static int dp(int i, int m)
    {
        if(i == N) return 0;
        else if(DP[i][m] != -1) return DP[i][m];
        if(m + P[i] <= M1 || m + P[i] <= M2)
        {
            int res1 = dp(i + 1, m);
            int res2 = dp(i + 1, m + P[i]);
            if(m + P[i] <= M1 || m + P[i] > 2000 || res2 > 0)
                DP[i][m] = Math.max(res1, F[i] + res2);
            else
                DP[i][m] = res1;
        }
        else
            DP[i][m] = dp(i + 1, m);
        max = Math.max(max, DP[i][m]);
        return DP[i][m];
    }
}
