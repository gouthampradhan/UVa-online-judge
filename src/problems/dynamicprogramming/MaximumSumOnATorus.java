package problems.dynamicprogramming;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 26/05/2016.
 * Accepted 0.440 s. Interesting problem, tricky 2-D max range sum. Requires some modification to original 2D matrix.
 * Algorithm DP O(N ^ 4)
 */
public class MaximumSumOnATorus
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
    private static int R[][];
    private static int max;
    private static int N, T;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        T = MyScanner.readInt();
        while(T-- > 0)
        {
            while((N = MyScanner.readInt()) == -1);
            R = new int[N * 2 + 1][N * 2 + 1];
            max = Integer.MIN_VALUE;
            for(int i = 1; i <= N; i ++)
                for(int j = 1; j <= N; j ++)
                {
                    int in = MyScanner.readInt();
                    R[i][j] = in;
                    R[i + N][j] = in;
                    R[i][j + N] = in;
                    R[i + N][j + N] = in;
                }
            for(int i = 1, s = N * 2; i <= s; i ++)
                for(int j = 1; j <= s; j ++)
                    R[i][j] = R[i][j] + R[i][j - 1] + R[i - 1][j] - R[i - 1][j - 1];

            for(int i = 1; i <= N; i ++)
                for(int j = 1; j <= N; j ++)
                    for(int k = i; k < i + N; k++)
                        for(int l = j; l < j + N; l++)
                        {
                            int sum = R[k][l] - R[k][j - 1] - R[i - 1][l] + R[i - 1][j - 1];
                            max = Integer.max(max, sum);
                        }
            pw.println(max);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
