package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 09/06/2016.
 * Accepted 0.290. Simple Knapsack DP algorithm. WA on multiple ocations because of Math.ceil.
 */
public class LetsYumCha
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
    private static int N, X, T, K, max;
    private static int[][][] DP;
    private static int[][] DimCha;

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
            X = MyScanner.readInt();
            T = MyScanner.readInt();
            K = MyScanner.readInt();
            DimCha = new int[K * 2][N + 2];
            DP = new int[K * 2][(N + 2) * 2][X * (N + 2)];
            for(int i = 0; i < (K * 2); i ++)
                for(int j = 0; j <= ((N + 1) * 2); j ++)
                    Arrays.fill(DP[i][j], -1);
            max = Integer.MIN_VALUE;
            for(int i = 0; i < K * 2; i += 2)
            {
                int[] priceFavor = new int[N + 2];
                for (int j = 0; j < N + 2; j++)
                    priceFavor[j] = MyScanner.readInt();

                DimCha[i] = priceFavor;
                DimCha[i + 1] = priceFavor;
            }
            dp(0, 0, 0);
            pw.println(String.format("%.2f", ((double)max / (N + 1))));
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * DP to find max favour
     * @param i dim cha index
     * @param totalCost total money remaining
     * @return
     */
    private static int dp(int i, int totalDish, int totalCost)
    {
        if(i == (K * 2)) return 0;
        if(DP[i][totalDish][totalCost] != -1) return DP[i][totalDish][totalCost];
        else
        {
            if((totalDish + 1) <= ((N + 1) * 2))
            {
                int tempTotal = totalCost + DimCha[i][0];
                tempTotal += (T * (N + 1));
                tempTotal *= 11;
                tempTotal = ((tempTotal % 10) == 0) ? (tempTotal / 10) : ((tempTotal / 10) + 1); //Math.ceil doesn't work
                if(tempTotal <= (X * (N + 1)))
                {
                    int favor = 0;
                    for(int j = 1; j <= N + 1; j ++)
                        favor += DimCha[i][j];
                    DP[i][totalDish][totalCost] = Math.max(dp(i + 1, totalDish, totalCost), favor + dp(i + 1, totalDish + 1, totalCost + DimCha[i][0]));
                }
                else
                    DP[i][totalDish][totalCost] = dp(i + 1, totalDish, totalCost);
            }
            else
            {
               if(DP[i][totalDish][totalCost] == -1)
                   DP[i][totalDish][totalCost] = 0;
            }
            max = Math.max(max, DP[i][totalDish][totalCost]);
        }
        return DP[i][totalDish][totalCost];
    }

}
