package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 06/06/2016.
 * Accepted 0.100 s. Simple Knapsack DP algorithm but a bit tricky to store DP values with mod values.
 */
public class DivisibleGroupSums
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
    private static int N, Q, M, D;
    private static long sum, count;
    private static int[] A;
    private static long[][][] DP;
    private static final String SET = "SET ", QUERY = "QUERY ";

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        int set = 0;
        while(true)
        {
            while ((N = MyScanner.readInt()) == -1) ;
            Q = MyScanner.readInt();
            if(N == 0) break;
            A = new int[N];
            for(int i = 0; i < N; i ++)
                A[i] = MyScanner.readInt();
            pw.println(SET + ++set + ":");
            for(int i = 1; i <= Q; i++)
            {
                D = MyScanner.readInt();
                M = MyScanner.readInt();
                DP = new long[201][M + 1][40 + 1];
                for(int j = 0; j < 201; j++)
                    for(int k = 0; k < M + 1; k++)
                        Arrays.fill(DP[j][k], - 1);
                pw.println(QUERY + i + ": " + dp(0, 0, 0));
            }
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Mod function, handle -ve values
     * @param sum
     * @return
     */
    private static int getMod(long sum)
    {
        int mod = (int)(sum % D);
        if(mod < 0)
        {
            mod *= -1;
            mod += 20;
        }
        return mod;
    }

    /**
     * Knapsack DP algorithm with a simple mod function.
     * @param i
     * @param total
     * @param sum
     * @return
     */
    private static long dp(int i, int total, long sum)
    {
        int mod = getMod(sum);
        if(DP[i][total][mod] != -1) return DP[i][total][mod];
        else
        {
            if(total == M)
                return ((sum % D) == 0) ? 1 : 0;
            else
            {
                if(i != N)
                {
                    long ret1 = dp(i + 1, total, sum);
                    long ret2 = dp(i + 1, total + 1, sum + A[i]);
                    DP[i][total][mod] = ((ret1 == -1) ? 0 : ret1) + ((ret2 == -1) ? 0 : ret2);
                }
            }
        }
        return DP[i][total][mod];
    }
}
