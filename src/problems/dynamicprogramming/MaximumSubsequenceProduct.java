package problems.dynamicprogramming;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

    /**
     * Created by gouthamvidyapradhan on 19/05/2016.
     * Accepted 0.140s. Simple bottom up DP program. Understood the problem quite easily but had to think a lot to come up with the code. The initial code
     * that I came up with using Topdown approach was quite tedious and was not accurate. But, I think bottom up approach for this problem works efficiently
     * and is a bit easy to code.
     */
    public class MaximumSubsequenceProduct
    {
        /**
         * Scanner class
         *
         * @author gouthamvidyapradhan
         */
        static class MyScanner
        {
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
                    if(str == null) return Integer.MIN_VALUE;
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
            public static void close() throws Exception
            {
                br.close();
            }
        }

        private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
        private static BigInteger[][] DP;
        private static BigInteger max;
        private static List<Integer> A = new ArrayList<>();
        private static int N;

        /**
         * Main method
         * @param args
         */
        public static void main(String[] args) throws Exception
        {
            while(true)
            {
                int i = MyScanner.readInt();
                if(i == Integer.MIN_VALUE) break;
                else
                {
                    A.clear();
                    while(i != -999999)
                    {
                        A.add(i);
                        i = MyScanner.readInt();
                    }
                    N = A.size();
                    DP = new BigInteger[N + 1][N + 1];
                    max = BigInteger.valueOf(A.get(0));
                    for(int j = 0; j <= N; j++)
                        DP[j][N] = BigInteger.ONE;
                    dp();
                }
                pw.println(max);
            }
            pw.flush(); pw.close(); MyScanner.close();
        }

        /**
         * Dp to find the max product
         */
        private static void dp()
        {
            for(int i = N - 1; i >= 0; i--)
            {
                for(int j = N - 1; j >= i; j--)
                {
                    DP[i][j] = DP[i + 1][j + 1].multiply(BigInteger.valueOf(A.get(i)));
                    max = max.max(DP[i][j]);
                }
            }
        }
    }
