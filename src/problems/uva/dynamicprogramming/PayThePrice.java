package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 22/06/2016.
 * Accepted 0.220.
 * WTF . . !!! Took a whole week to figure out the DP algorithm. But, eventually, I figured out how DP works for this problem and how to identify the
 * sub-problems. I think its a very good learning, even though I spent a lot of time to understand DP algorithm for this problem, I now understand the
 * core of of DP algorithm ie to identify the sub problems.
 */
public class PayThePrice
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
    private static long [][] DP = new long[301][301];

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        for (int i = 0; i <= 300; i++)
            DP[i][0] = 1;
        dp();
        while(true)
        {
            String line = MyScanner.readLine();
            if(line == null) break;
            else if(line.trim().equals("")) continue;
            StringTokenizer st = new StringTokenizer(line);
            int count = st.countTokens();
            if(count == 1)
            {
                int value = Integer.parseInt(st.nextToken().trim());
                pw.println(DP[value][value]);
            }
            else if(count == 2)
            {
                int value = Integer.parseInt(st.nextToken().trim());
                int coin = Integer.parseInt(st.nextToken().trim());
                coin = (coin > 300) ? 300 : coin;
                pw.println(DP[coin][value]);
            }
            else
            {
                int value = Integer.parseInt(st.nextToken().trim());
                int coinLow = Integer.parseInt(st.nextToken().trim());
                int coinHigh = Integer.parseInt(st.nextToken().trim());
                if((coinHigh - coinLow) == coinHigh)
                    pw.println(DP[coinHigh][value]);
                else
                {
                    coinLow = (coinLow > 300) ? 300 : coinLow;
                    coinHigh = (coinHigh > 300) ? 300 : coinHigh;
                    pw.println(DP[coinHigh][value] - DP[coinLow - 1][value]);
                }
            }
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Calculate number of ways
     * @return
     */
    private static void dp()
    {
        for(int i = 1; i <= 300; i++)
            for(int j = 1; j <= 300; j++)
                DP[i][j] = (j >= i) ? (DP[i][j - i] + DP[i - 1][j]) : DP[i - 1][j];
    }
}
