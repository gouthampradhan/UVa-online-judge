package problems.uva.moreadvancedtopics;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 12/11/2016.
 * Accepted 0.170. Caution when performing bitwise operations on long values.
 */
public class AGentlemenAgreement
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 500000)); //set the buffer too high.
    private static int I, R, T, count;
    private static long[] M;
    private static int max = Integer.MIN_VALUE;
    private static long all_cities;

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
            I = MyScanner.readInt();
            R = MyScanner.readInt();
            M = new long[I];
            all_cities = (1L << I) - 1;
            for(int i = 0 ; i < I ; i ++)
                M[i] = (1L << i);
            count = 0; max = Integer.MIN_VALUE;
            for(int i = 0; i < R; i ++)
            {
                int u = MyScanner.readInt();
                int v = MyScanner.readInt();
                M[u] |= (1L << v);
                M[v] |= (1L << u);
            }

            for(int i = 0; i < I; i++)
                backTrack(M[i], i, 1);

            pw.println(count);
            pw.println(max);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Backtrack to all states to check the maximum
     * @param state
     * @param u
     * @param depth
     */
    private static void backTrack(long state, int u, int depth)
    {
        if(state == all_cities) //all cities covered
        {
            max = Math.max(max, depth);
            count++;
        }
        else
        {
            for(int i = u; i < I; i ++)
            {
                if(((1L << i) & (~state)) > 0)
                {
                    backTrack(state | M[i], i + 1, depth + 1);
                }
            }
        }
    }
}
