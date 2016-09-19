package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 24/05/2016.
 * Accepted 0.360s. Very interesting 3-D DP algorithm, was very confusing to understand the problem statement clearly. After reading about Max 2D array sum,
 * I was able to solve it with ease.
 *
 * My Algorithm. Use Max 2D array DP sum and then use the linear array max sum for the third dimension.
 * O(N ^ 5) + O(N ^ 3) + O(N ^ 2)
 */
public class GarbageHeap
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
    private static long G[][][];
    private static long max;
    private static long[] M;
    private static int A, B, C, T;

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
            while((A = MyScanner.readInt()) == -1);
            B = MyScanner.readInt();
            C = MyScanner.readInt();
            M = new long[A + 1];
            G = new long[A + 1][B + 1][C + 1];
            max = Long.MIN_VALUE;
            for(int i = 1; i <= A; i ++)
                for(int j = 1; j <= B; j ++)
                    for(int k = 1; k <= C; k ++)
                    {
                        long value = MyScanner.readLong();
                        value += G[i][j - 1][k];
                        value += G[i][j][k - 1];
                        value -= G[i][j - 1][k - 1];
                        G[i][j][k] = value;
                    }

            for(int i = 1; i <= B; i ++)
                for(int j = 1; j <= C; j ++)
                    for(int k = i; k <= B; k ++)
                        for(int l = j; l <= C; l ++)
                        {
                            for(int m = 1; m <= A; m ++)
                            {
                                long sum = G[m][k][l] - G[m][k][j - 1] -  G[m][i - 1][l] + G[m][i - 1][j - 1];
                                M[m] = Math.max(sum, M[m - 1] + sum);
                                max = Math.max(max, M[m]);
                            }
                        }
            if(count++ > 0)
                pw.println();
            pw.println(max);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
