package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 29/05/2016.
 * Accepted 0.300 s. Simple LIS DP, struggled a bit on LIS implementation.
 * Have to be careful with forloops and not re-calculate the values for repeated overlapping subproblems
 * O(N ^ 2)
 */
public class Trainsorting
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
    private static int T, N, max;
    private static int[] C;
    private static int[][] DP;

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
            while((N = MyScanner.readInt()) == -1);
            if(N == 0)
            {
                pw.println(0);
                continue;
            }
            C = new int[N];
            for(int i = 0; i < N; i++)
                C[i] = MyScanner.readInt();
            max = Integer.MIN_VALUE;
            DP = new int[N][2];
            for(int i = 0; i < N; i++)
            {
                DP[i][0] = 0; //LIS
                DP[i][1] = 0; //LDS
            }
            LIS(0);
            LDS(0);
            for(int i = 0; i < N; i++)
                max = Integer.max(max, DP[i][0] + DP[i][1]);
            pw.println(max - 1);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Longest increasing sub-sequence
     * @param n
     * @return
     */
    private static int LIS(int n)
    {
        if(DP[n][0] != 0) return DP[n][0];
        for(int i = n; i < N; i++)
        {
            if(DP[i][0] == 0) //very important. Else the values for overlapping subproblems will be recalculated which results in TLE
            {
                for (int j = i + 1; j < N; j++) {
                    if (C[j] > C[i]) {
                        int value = LIS(j);
                        DP[i][0] = Integer.max(DP[i][0], value + 1);
                    }
                }
                DP[i][0] = (DP[i][0] == 0) ? 1 : DP[i][0];
            }
        }
        return DP[n][0];
    }

    /**
     * Longest decreasing sub-sequence
     * @param n
     * @return
     */
    private static int LDS(int n)
    {
        if(DP[n][1] != 0) return DP[n][1];
        for(int i = n; i < N; i++)
        {
            if(DP[i][1] == 0) //very important. Else the values for overlapping subproblems will be recalculated which results in TLE
            {
                for (int j = i + 1; j < N; j++)
                {
                    if (C[j] < C[i])
                    {
                        int value = LDS(j);
                        DP[i][1] = Integer.max(DP[i][1], value + 1);
                    }
                }
                DP[i][1] = (DP[i][1] == 0) ? 1 : DP[i][1];
            }
        }
        return DP[n][1];
    }

}
