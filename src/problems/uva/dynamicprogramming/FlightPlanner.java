package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 25/08/2016.
 * Accepted 0.040. Simple DP on DAG
 */
public class FlightPlanner
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

    private static int T, X, D;
    private static int[][] DP, W;
    private static final int[] C = {1, 1, 1};
    private static final int[] R = {0, 1, -1};
    private static final int[] F = {30, 60, 20};
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        T = MyScanner.readInt();
        while(T-- > 0)
        {
            while((X = MyScanner.readInt()) == -1);
            D = X / 100;
            W = new int[10][D];
            DP = new int[10][D];
            for(int i = 0; i < 10; i ++)
                Arrays.fill(DP[i], Integer.MAX_VALUE);
            for(int i = 9; i >= 0; i --)
            {
                for(int j = 0; j < D; j ++)
                    W[i][j] = MyScanner.readInt() * -1;
            }
            DP[1][D - 1] = 20 + W[1][D - 1]; //landing point
            DP[0][D - 1] = 30 + W[0][D - 1]; //landing point
            pw.println(dp(0, 0));
            pw.println();
        }
        pw.flush();
        pw.close();
        MyScanner.close();
    }

    /**
     * Dp to fetch the min fuel required.
     * @param r Row
     * @param c Col
     * @return
     */
    private static int dp(int r, int c)
    {
        if(DP[r][c] == Integer.MAX_VALUE)
        {
            for(int i = 0; i < 3; i ++)
            {
                int newR = r + R[i];
                int newC = c + C[i];
                if(newR >= 10 || newR < 0 || newC >= D)
                    continue;
                int ret = dp(r + R[i], c + C[i]);
                if(ret != Integer.MAX_VALUE)
                {
                    DP[r][c] = Math.min(DP[r][c], W[r][c] + F[i] + ret);
                }
            }
        }
        return DP[r][c];
    }
}
