package problems.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 14/06/2016.
 * Accepted 100s. Simple DP problem but really struggled to think of the solution and implement simply because the approach suggested by
 * the Felix-Halim book is straight-forward approach. This approach below is more natural and simple.
 * The approach below can be used for usual coin-change problem where the number of coins are not limited and also can be used for the below problem
 * where number of coins to be used is only limited to the ones provided in the input.
 */
public class ExactChange
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
    private static int N, T, B;
    private static int[] DP, V;

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
            while((B = MyScanner.readInt()) == -1);
            N = MyScanner.readInt();
            V = new int[N];
            DP = new int[10001];
            Arrays.fill(DP, Integer.MAX_VALUE);
            DP[0] = 0;
            for(int i = 0; i < N; i ++)
                V[i] = MyScanner.readInt();

            for(int i = 0; i < N; i++)
                for(int j = 10000; j >= 0; j--)
                    if(DP[j] != Integer.MAX_VALUE && (j + V[i] <= 10000))
                        DP[j + V[i]] = Math.min(DP[j + V[i]], DP[j] + 1);

            for(int i = B; i <= 10000; i++)
                if(DP[i] != Integer.MAX_VALUE)
                {
                    pw.println(i + " " + DP[i]);
                    break;
                }
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
