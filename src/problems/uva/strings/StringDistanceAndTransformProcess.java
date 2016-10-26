package problems.uva.strings;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 26/10/2016.
 * Accepted 0.160s
 */
public class StringDistanceAndTransformProcess
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

    enum Direction
    {
        LEFT,
        UP,
        DIAGONAL,
    }

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 100000));
    private static int[][] dp;
    private static Direction[][] D;
    private static StringBuilder editString1, editString2;
    private static char[] strArr1, strArr2;

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        int t = 0;
        while(true)
        {
            String str1 = MyScanner.readLine();
            if(str1 == null || str1.trim().equals("")) break;
            String str2 = MyScanner.readLine();
            if(t++ != 0)
                pw.println();
            int R = str1.length();
            int C = str2.length();
            dp = new int[R + 1][C + 1];
            D = new Direction[R + 1][C + 1];
            strArr1 = str1.toCharArray();
            strArr2 = str2.toCharArray();
            editString1 = new StringBuilder();
            editString2 = new StringBuilder();
            //pre-fill base case
            for(int i = 0; i < R + 1; i ++)
            {
                for(int j = 0; j < C + 1; j ++)
                {
                    if(i == 0)
                    {
                        dp[i][j] = j;
                        D[i][j] = Direction.LEFT;
                    }
                    else if(j == 0)
                    {
                        dp[i][j] = i;
                        D[i][j] = Direction.UP;

                    }
                    else if(strArr1[i - 1] == strArr2[j - 1])
                    {
                        dp[i][j] = dp[i - 1][j - 1];
                        D[i][j] = Direction.DIAGONAL;
                    }
                    else
                    {
                        if(dp[i - 1][j - 1] < dp[i][j - 1] && dp[i - 1][j - 1] < dp[i - 1][j])
                        {
                            dp[i][j] = dp[i - 1][j - 1] + 1;
                            D[i][j] = Direction.DIAGONAL;
                        }
                        else if(dp[i][j - 1] < dp[i - 1][j])
                        {
                            dp[i][j] = dp[i][j - 1] + 1;
                            D[i][j] = Direction.LEFT;
                        }
                        else
                        {
                            dp[i][j] = dp[i - 1][j] + 1;
                            D[i][j] = Direction.UP;
                        }
                    }
                }
            }
            buildString(R, C);
            int index = 1, row = 1;
            pw.println(dp[R][C]);
            for(int i = 0, l = editString1.length(); i < l; i ++)
            {
                if(editString2.charAt(i) == '_')
                {
                    pw.println(row++ + " Delete " + index);
                    continue;
                }
                if(editString1.charAt(i) == '_')
                    pw.println(row++ + " Insert " + index + "," + editString2.charAt(i));
                else if(editString1.charAt(i) != editString2.charAt(i))
                    pw.println(row++ + " Replace " + index + "," + editString2.charAt(i));
                index++;
            }
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Build string
     * @param r row
     * @param c column
     */
    private static void buildString(int r, int c)
    {
        if(r == 0 && c == 0) return;

        switch (D[r][c])
        {
            case DIAGONAL:
                 buildString(r - 1, c - 1);
                 editString1.append(String.valueOf(strArr1[r - 1]));
                 editString2.append(String.valueOf(strArr2[c - 1]));
                 break;

            case LEFT:
                buildString(r, c - 1);
                editString1.append("_");
                editString2.append(String.valueOf(strArr2[c - 1]));
                break;

            case UP:
                buildString(r - 1, c);
                editString1.append(String.valueOf(strArr1[r - 1]));
                editString2.append("_");
                break;
        }
    }
}
