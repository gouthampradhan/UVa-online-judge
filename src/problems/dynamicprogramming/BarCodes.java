package problems.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 25/08/2016.
 * Accepted 0.040s. Simple DP.
 * O(N x K x M)
 */
public class BarCodes
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
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static double readDouble() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return Double.parseDouble(st.nextToken());
                }
                String str = br.readLine();
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return Double.parseDouble(st.nextToken());
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

    private static int N, K, M;
    private static long[][] DP;
    private static long sum;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            while((N = MyScanner.readInt()) == -1);
            if(N == -2) break;
            K = MyScanner.readInt();
            M = MyScanner.readInt();
            DP = new long[N + 1][K + 1];
            for(int i = 0; i <= N; i++)
                Arrays.fill(DP[i], -1L);
            pw.println(dp(N, K));
        }
        pw.close();
        MyScanner.close();
    }

    private static long dp(int n, int k)
    {
        if(n == 0 && k == 0)
            return 1;
        else if(n < 0 || k < 0) return 0;
        else if(n > 0 && k == 0) return 0;
        if(DP[n][k] == -1)
        {
            sum = 0L;
            for(int i = 1; i <= M; i++)
                sum += dp(n - i, k - 1);
            DP[n][k] = sum;
        }
        return DP[n][k];
    }
}
