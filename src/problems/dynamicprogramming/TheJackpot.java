package problems.dynamicprogramming;

/**
 * Created by gouthamvidyapradhan on 23/05/2016.
 * Accepted 0.210s. Simple DP algorithm. WA initially when used a simple Array for storing the previous sum, but works okay when a simple variable sum is used.
 *
 * Found out the root cause of the WA - The input contains blank lines and hence when blank lines were not added then the below code creates an array of size
 * int [N + 1] => int [0] and since the size of array is 0, this will not execute any for loops below and hence prints a WA.
 *
 * NEVER FORGET TO HANDLE BLANK LINES USE : while((N = MyScanner.readInt()) == -1);
 */

import java.io.*;
import java.util.StringTokenizer;

class TheJackpot
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
    private static int I[];
    private static int N, max;
    private static final String LOOSING = "Losing streak.", WIN = "The maximum winning streak is ";

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            while((N = MyScanner.readInt()) == -1);
            if(N == 0) break;
            I = new int[N + 1];
            for(int i = 1; i <= N; i++)
                I[i] = MyScanner.readInt();
            max = Integer.MIN_VALUE;
            for(int i = 1; i <= N; i++)
            {
                I[i] = Math.max(I[i], (I[i] + I[i - 1]));
                max = Math.max(I[i], max);
            }
            pw.println((max > 0) ? WIN + max + "." : LOOSING);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
