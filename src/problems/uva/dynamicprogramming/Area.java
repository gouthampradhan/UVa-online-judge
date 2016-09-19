package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.*;

/**
 * Created by gouthamvidyapradhan on 26/05/2016.
 * Accepted 0.540 : Simple 2-D Range sum DP algorithm. Requires pruning to pass the time limit.
 * O(N ^ 4) algorithm.
 */
public class Area
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
    private static long P[][];
    private static int max_area;
    private static long min_price;
    private static int N, M, K, T;
    private static final String CASE = "Case #";

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
            N = MyScanner.readInt();
            M = MyScanner.readInt();
            K = MyScanner.readInt();
            P = new long[N + 1][M + 1];
            min_price = Integer.MAX_VALUE;
            max_area = Integer.MIN_VALUE;
            for(int i = 1; i <= N; i++)
                for(int j = 1; j <= M; j++)
                {
                    int p = MyScanner.readInt();
                    P[i][j] = p + P[i][j - 1] + P[i - 1][j] - P[i - 1][j - 1];
                }
            for(int i = 1; i <= N; i ++)
                for(int j = 1; j <= M; j ++)
                    for(int k = i; k <= N; k++)
                        for(int l = (max_area == Integer.MIN_VALUE) ? j : (j + (max_area / k) - 1); l <= M; l++) //prune the search space using area = a * b
                        {
                            long price = P[k][l] - P[k][j - 1] - P[i - 1][l] + P[i - 1][j - 1];
                            if(price <= K)
                            {
                                int area = (k - i + 1) * (l - j + 1);
                                if(area > max_area)
                                {
                                    max_area = area;
                                    min_price = price;
                                }
                                else if(area == max_area)
                                    min_price = Math.min(min_price, price);
                            }
                        }
            pw.println(CASE + ++count + ": " + ((max_area == Integer.MIN_VALUE) ? 0 : max_area) + " " + ((min_price == Integer.MAX_VALUE) ? 0 : min_price));
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
