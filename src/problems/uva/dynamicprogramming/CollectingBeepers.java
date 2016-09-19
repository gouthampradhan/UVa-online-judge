package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 09/07/2016.
 * Accepted 0.030 s. Simple TSP DP algorithm.
 */
public class CollectingBeepers
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
    private static int[][] G, DP;
    private static int N, T;
    private static Point[] B;
    private static final String SHPATH = "The shortest path has length ";

    private static class Point
    {
        int r, c;
        Point(int r, int c)
        {
            this.r = r;
            this.c = c;
        }
    }

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
            MyScanner.readInt(); MyScanner.readInt(); //ignore
            Point start = new Point(MyScanner.readInt(), MyScanner.readInt());
            N = MyScanner.readInt();
            B = new Point[N + 1];
            B[0] = start;
            G = new int[N + 1][N + 1];
            for(int i = 1; i <= N; i++)
                B[i] = new Point(MyScanner.readInt(), MyScanner.readInt());
            for(int i = 0; i < N + 1; i++)
            {
                for (int j = i + 1; j < N + 1; j++)
                {
                    G[i][j] = Math.abs(B[i].r - B[j].r) + Math.abs(B[i].c - B[j].c);
                    G[j][i] = G[i][j];
                }
            }
            DP = new int[N + 1][(int)Math.pow(2, N + 1)];
            for(int i = 0; i < N + 1; i++)
                Arrays.fill(DP[i], Integer.MAX_VALUE);
            for(int i = 0; i < N + 1; i++)
                DP[i][(int)Math.pow(2, N + 1) - 1] = G[i][0];
            pw.println(SHPATH + dp(0, 1));
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * TSP DP algorithm
     * @param pos
     * @param state
     * @return
     */
    private static int dp(int pos, int state)
    {
        if(DP[pos][state] != Integer.MAX_VALUE)
            return DP[pos][state];
        else
        {
            for(int i = 0; i < N + 1; i++)
            {
                if(((1 << i) & state) == 0)
                {
                    int newState = state | (1 << i);
                    int value = dp(i, newState);
                    DP[pos][state] = Math.min(DP[pos][state], G[i][pos] + value);
                }
            }
        }
        return DP[pos][state];
    }

}
