package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 12/06/2016.
 * Accepted 0.100 s. Time limit is for the entire test case and not for each test case !! This is very different from other problems.
 * O(N x 5)
 * Simple problem, but really struggled to figure out the algorithm - A varient of coin change problem.
 */
public class LetMeCountTheWays
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
    private static int N;
    private static long [][] DP = new long[5][30001];
    private static final int[] V = {1, 5, 10, 25, 50};

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        //Tricky input, the time limit is for the entire test case and not for each test case !!
        for(int i = 0; i < 5; i ++)
            for(int j = 0; j <= 30000; j ++)
                DP[i][j] = -1;
        dp(0, 30000);

        while(true)
        {
            while((N = MyScanner.readInt()) == -1);
            if(N == -2) break;
            long count = dp(0, N);
            if(count == 1)
                pw.println("There is only 1 way to produce " + N + " cents change.");
            else
                pw.println("There are " + count + " ways to produce " + N + " cents change.");
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * DP to calculate different ways
     * @param i index of coin type
     * @param s total sum
     * @return
     */
    private static long dp(int i, int s)
    {
        if(i == 5) return 0;
        else if(s < 0) return 0;
        else if(s == 0) return 1;
        if(DP[i][s] != -1) return DP[i][s];
        DP[i][s] = dp(i, s - V[i]) + dp(i + 1, s);
        return DP[i][s];
    }
}
