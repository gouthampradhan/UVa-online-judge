package problems.uva.strings;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 23/10/2016.
 * Accepted 0.060
 */
public class ScrollingSign
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
    private static int T, K, W, N, indexOf;
    private static int[] b = new int[101];

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        T = MyScanner.readInt();
        while(T -- > 0)
        {
            while((K = MyScanner.readInt()) == -1);
            W = MyScanner.readInt();
            StringBuilder main = new StringBuilder(MyScanner.readLine().trim()); //read the first line
            W--;
            while(W-- > 0)
            {
                String line = MyScanner.readLine().trim();
                preProcess(line);
                indexOf = match(line, main.toString());
                main.append(line.substring(indexOf));
            }
            pw.println(main.length());
            pw.flush();
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Preprocess string
     * @param pattern Pattern
     */
    private static void preProcess(String pattern)
    {
        int i = 0, j = -1;
        b[0] = -1;
        char[] cArr = pattern.toCharArray();
        N = pattern.length();
        while(i < N)
        {
            while(j >= 0 && cArr[i] != cArr[j]) j = b[j];
            j++; i++;
            b[i] = j;
        }
    }

    /**
     * Return last index of
     * @param pattern pattern string
     * @param main main string
     * @return
     */
    private static int match(String pattern, String main)
    {
        int i = 0, j = 0, l;
        char[] pArr = pattern.toCharArray();
        char[] mStr = main.toCharArray();
        N = main.length();
        l = pArr.length;
        while(i < N)
        {
            while(j >= 0 && pArr[j] != mStr[i]) j = b[j];
            j++; i++;
            if(j == l && i != N)
                j = b[j];
        }
        return j;
    }
}
