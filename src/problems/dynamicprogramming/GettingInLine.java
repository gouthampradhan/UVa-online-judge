package problems.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 28/06/2016.
 * Accepted 0.070s. Simple problem but really struggled to understand the DP form of TSP.
 * O( 2 ^ N * N * N * N)
 * Problem involves constructing the TSP DP weight array and also a separate array to store the path.
 */
public class GettingInLine
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
    private static int[][] P;
    private static double[][] G, W;
    private static int[] R;
    private static int N;
    private static double min;
    private static final int CONST = 15;
    private static Point[] Po;

    /**
     *
     */
    private static class Point
    {
        int x, y;
        Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        int T = 0;
        while(true)
        {
            N = MyScanner.readInt();
            G = new double[N][N];
            if(N == 0) break;
            Po = new Point[N];
            int start = 0;
            for(int i = 0; i < N; i++)
                Po[i] = new Point(MyScanner.readInt(), MyScanner.readInt());
            for(int i = 0; i < N; i ++)
                for(int j = 1; j < N; j ++)
                {
                    Point p1 = Po[i];
                    Point p2 = Po[j];
                    int x = (p1.x - p2.x) * (p1.x - p2.x);
                    int y = (p1.y - p2.y) * (p1.y - p2.y);
                    G[i][j] = Math.sqrt(x + y) + 16;
                    G[j][i] = Math.sqrt(x + y) + 16;
                }
            W = new double[N][(int)Math.pow(2, N)];
            P = new int[N][(int)Math.pow(2, N)];
            R = new int[N];
            min = Double.MAX_VALUE;
            for(int i = 0; i < N; i ++)
            {
                for(int j = 0; j < N; j++)
                    Arrays.fill(W[j], Double.MAX_VALUE);
                for(int j = 0; j < N; j++)
                    W[j][(int)Math.pow(2, N) - 1] = 0.0D;
                double ans = dp(((1 << i) << 4) + i);
                if(ans < min)
                {
                    min = ans;
                    start = i;
                    path(i, (1 << i), 0);
                }
            }
            pw.println("**********************************************************");
            pw.println("Network #" + ++T);
            print(start, 0);
            pw.println("Number of feet of cable required is " + String.format("%.2f", min) + ".");
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Print path
     * @param from
     * @param count
     */
    private static void print(int from, int count)
    {
        if(count < N - 1)
        {
            pw.print("Cable requirement to connect ");
            int to = R[from];
            pw.print("(" + Po[from].x + "," + Po[from].y + ")");
            pw.print(" to ");
            pw.print("(" + Po[to].x + "," + Po[to].y + ")");
            pw.print(" is ");
            pw.print(String.format("%.2f", G[from][to]));
            pw.println(" feet.");
            print(to, ++count);
        }
    }

    /**
     * Save the path indices
     * @param i
     * @param state
     * @param count
     */
    private static void path(int i, int state, int count)
    {
        if(count < N)
        {
            int v = P[i][state];
            R[i] = v & CONST;
            path(v & CONST, v >> 4, ++count);
        }
    }

    /**
     * TSP Dp algorithm
     * @param state
     * @return
     */
    private static double dp(int state)
    {
        int i = state & CONST;
        int subState = state >> 4;
        if(W[i][subState] != Double.MAX_VALUE)
            return W[i][subState];
        else
        {
            for(int j = 0; j < N; j++)
            {
                if((subState & (1 << j)) == 0)
                {
                    int newSubState = subState | ((1 << j));
                    double value = dp((newSubState << 4) + j);
                    if((value + G[i][j]) < W[i][subState])
                    {
                        W[i][subState] = value + G[i][j];
                        P[i][subState] = (newSubState << 4) + j;
                    }
                }
            }
        }
        return W[i][subState];
    }
}
