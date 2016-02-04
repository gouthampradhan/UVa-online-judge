package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.169. Really struggled to understand the DP algorithm. When creating a graph using DP approach, I have to be more careful because the graph 
 * is only imaginary and does not actually exists and hence the iteration of child nodes can be very tricky. 
 * 
 * The algorithm suggested in Felix Halim does not work, got TLE many times. This problem has to be solved from the finish node (N+1) and not from 0 
 *
 */
public class WinterimBackpackingTrip {

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
                if (str != null && !str.trim().equals("")) {
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
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
            return br.ready();
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

            // Build the number
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int[][] min = new int[310][610];
    private static boolean[][] done = new boolean[310][610];
    private static int[] input = new int[610], dist = new int[610];
    private static int N, K;
    private static final int MAX_VALUE = Integer.MAX_VALUE;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            while((N = MyScanner.readInt()) == -1);
            if(N == -2) break;
            K = MyScanner.readInt();
            int minVal = -1;
            //construct the distance graph
            for(int i = 0; i<=K+1; i++)
                for(int j = 0; j<=N+1; j++)
                    {min[i][j] = MAX_VALUE; done[i][j] = false;} //initialize with max value
            //accept input
            for(int i=1; i<=N+1; i++)
            {
                int in = MyScanner.readInt();
                input[i] = in; //input array
                dist[i] = dist[i-1] + in;//sum of distances
                min[1][i] = dist[i]; //sum up the minimum. This is the final node, can't go beyond this.
                done[1][i] = true; // mark this as finished. This is the final node, can't go beyond this.
                minVal = Math.max(minVal, in); //maintain a minimum
            }
            if(K == 0)
                pw.println(dist[N+1]);
            else if(K > N)
                pw.println(minVal);
            else
            	pw.println(dp(N+1, K+1));
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Perform a recursive dp to fetch the min of maximum
     * @param n current node being examined
     * @param k number of nights left
     * @return Min of Max value
     */
    private static int dp(int n, int k)
    {
        if(done[k][n]) return min[k][n];
        else
        {
        	done[k][n] = true;
            for(int i = n-1; i >= k-1; i--) // i >= k-1 very important. Does not work with i > 0 
            	//because it will actually iterate through all the child nodes which is not necessary
            {
                int temp = Math.max(dp(i, k-1), dist[n] - dist[i]);
                if(temp < min[k][n])
                    min[k][n] = temp;
            }
        }
        return min[k][n];
    }
}