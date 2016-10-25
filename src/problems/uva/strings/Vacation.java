package problems.uva.strings;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 24/10/2016.
 * Accepted 0.040s
 */
public class Vacation
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
    private static int dp[][];
    private static int max;

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        int T = 1;
        while(true)
        {
            String line = MyScanner.readLine();
            if(line.trim().equals("#")) break;
            char[] mom = line.toCharArray();
            char[] dad = MyScanner.readLine().toCharArray();
            dp = new int[mom.length + 1][dad.length + 1];
            for(int i = 0, l = mom.length + 1; i < l; i++)
                Arrays.fill(dp[i], 0);
            max = 0;
            for (int i = 1, l1 = mom.length; i <= l1; i ++)
            {
                for (int j = 1, l2 = dad.length; j <= l2; j ++)
                {
                    if(mom[i - 1] == dad[j - 1])
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    else if(dp[i - 1][j] >= dp[i][j - 1])
                        dp[i][j] = dp[i - 1][j];
                    else dp[i][j] = dp[i][j - 1];
                    max = Math.max(max, dp[i][j]);
                }
            }
            pw.println("Case #" + T++ + ": you can visit at most " + max + " cities.");
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
