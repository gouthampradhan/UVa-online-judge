package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 18/06/2016.
 * Accepted 0.080 s. Simple coin change DP algorithm. The coin has two parts hence use a 2-D DP array.
 */
public class ECoins
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
    private static int T, M, S;
    private static int[][] DP;
    private static int[] X, Y;
    private static final String NOT_POSSIBLE = "not possible";

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
            while((M = MyScanner.readInt()) == -1);
            S = MyScanner.readInt();
            X = new int[M];
            Y = new int[M];
            DP = new int[S + 1][S + 1];
            for(int i = 0; i < M; i++)
            {
                X[i] = MyScanner.readInt();
                Y[i] = MyScanner.readInt();
            }
            for(int i = 0; i <= S; i++)
                Arrays.fill(DP[i], Integer.MAX_VALUE);
            DP[0][0] = 0;
            for(int c = 0; c < M; c ++)
                for(int i = 0; i <= S && ((i + X[c]) <= S); i ++)
                    for(int j = 0; j <= S && ((j + Y[c]) <= S); j ++)
                        if(DP[i][j] != Integer.MAX_VALUE)
                            DP[i + X[c]][j + Y[c]] = Math.min(DP[i][j] + 1, DP[i + X[c]][j + Y[c]]);

            int min = Integer.MAX_VALUE;
            for(int i = 0; i <= S; i++)
                for(int j = 0; j <= S; j++)
                    if(((i * i) + (j * j)) == S * S && DP[i][j] != Integer.MAX_VALUE)
                        min = Math.min(min, DP[i][j]);
            if(min == Integer.MAX_VALUE)
                pw.println(NOT_POSSIBLE);
            else pw.println(min);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
