package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 18/06/2016.
 * Accepted 0.060. Quite challenging coin change problem, similar to ExactChange. The input is missing a key data, the
 * count of coins is not mentioned hence the worst time complexity is difficult to determine.
 * But algorithm runs in O(N x 500) where N is total number of coins
 * @see ExactChange
 */
public class MakingChange
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
    private static int S;
    private static int[] CU, SK, C;
    private static final int[] V = {5, 10, 20, 50, 100, 200};

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
            StringTokenizer st = new StringTokenizer(line);
            C = new int[6];
            for(int i = 0; i < 6; i++)
                C[i] = Integer.parseInt(st.nextToken().trim());
            if(C[0] == 0 && C[1] == 0 && C[2] == 0 && C[3] == 0 && C[4] == 0 && C[5] == 0)
                break;
            StringTokenizer st1 = new StringTokenizer(st.nextToken(), ".");
            S = (Integer.parseInt(st1.nextToken().trim()) * 100) + Integer.parseInt(st1.nextToken().trim());
            int sum = 0;
            for(int i = 0; i < 6; i++)
                sum += C[i] * V[i];
            CU = new int[1001]; //Customer
            SK = new int[sum + 1]; //Shopkeeper
            Arrays.fill(SK, Integer.MAX_VALUE);
            Arrays.fill(CU, Integer.MAX_VALUE);
            SK[0] = 0; CU[0] = 0;
            for(int i = 0; i < 6; i ++)
            {
                for(int j = 0; j <= sum && ((j + V[i]) <= sum); j ++)
                    if(SK[j] != Integer.MAX_VALUE)
                        SK[j + V[i]] = Math.min(SK[j] + 1, SK[j + V[i]]);
            }
            for(int i = 0; i < 6; i ++)
            {
                for(int k = 0, count = C[i]; k < count; k++)
                {
                    for(int j = 1000; j >= 0; j--)
                    {
                        if(CU[j] != Integer.MAX_VALUE && (j + V[i]) <= 1000)
                            CU[j + V[i]] = Integer.min(CU[j + V[i]], CU[j] + 1);
                    }
                }
            }
            int min = CU[S];
            int change;
            for(int i = S + 1; (i <= 1000) && ((i - S) <= sum); i++)
            {
                change = i - S;
                if(SK[change] != Integer.MAX_VALUE && CU[i] != Integer.MAX_VALUE)
                    min = Math.min(min, CU[i] + SK[change]);
            }
            pw.printf("%3d", min);
            pw.println();
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
