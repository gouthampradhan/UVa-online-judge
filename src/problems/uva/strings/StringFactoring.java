package problems.uva.strings;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 31/10/2016.
 * Accepted 0.050 s
 */
public class StringFactoring
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 100000));
    private static int[][] dp;
    private static char[] cArr;
    private static int N;

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            String line = MyScanner.readLine();
            if(line.equals("*")) break;
            N = line.length();
            cArr = line.toCharArray();
            dp = new int[N][N];
            for(int i = 0; i < N; i ++)
                Arrays.fill(dp[i], Integer.MAX_VALUE);
            pw.println(dp(0, line.length() - 1));
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * DP to find the minimum factoring
     * @param l left index
     * @param r right index
     * @return min string factor
     */
    private static int dp(int l, int r)
    {
        if(l == r) return 1;
        if(dp[l][r] != Integer.MAX_VALUE) return dp[l][r];
        for(int i = l; i < r; i ++)
        {
            dp[l][r] = Math.min(dp[l][r], dp(l, i) + dp(i + 1, r));

            //check same
            boolean same = true;
            int leftLen = (i - l) + 1;
            if(leftLen > (r - i) || ((r - i) % leftLen) != 0)
                same = false;
            else
            {
                for(int j = l, k = i + 1; j <= i && k <= r; j = (j == i) ? l : j + 1, k++)
                {
                    if(cArr[j] != cArr[k])
                    {
                        same = false;
                        break;
                    }
                }
            }
            if(same)
                dp[l][r] = Math.min(dp[l][r], dp(i + 1, r));
        }
        return dp[l][r];
    }
}
