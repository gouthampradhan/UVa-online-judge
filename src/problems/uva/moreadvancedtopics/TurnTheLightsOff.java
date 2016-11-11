package problems.uva.moreadvancedtopics;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 10/11/2016.
 * Accepted 0.060 s
 */

public class TurnTheLightsOff
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
    private static int A[], B[];
    private static int N;


    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        String test;
        while(true)
        {
            while((test = MyScanner.readLine()) == null || (test.trim().isEmpty()));
            if(test.trim().equalsIgnoreCase("end")) break;
            A = new int[10];
            B = new int[10];
            for(int i = 0; i < 10; i ++)
            {
                String line;
                while((line = MyScanner.readLine()) == null || (line.trim().isEmpty()));
                for(int j = 0, l = line.length(); j < l; j ++)
                {
                    if(line.charAt(j) == 'O')
                        B[i] |= (1 << j);
                }
            }

            int max = 1024, count, min = 100;
            for(int i = 0; i < max; i ++)
            {
                System.arraycopy(B, 0, A, 0, 10);
                count = 0;
                for(int j = 0; j < 10; j ++)
                {
                    if(((1 << j) & i) > 0)
                    {
                        A[0] ^= (1 << j);
                        A[1] ^= (1 << j);
                        if(j - 1 >= 0)
                            A[0] ^= (1 << (j - 1));
                        if(j + 1 < 10)
                            A[0] ^= (1 << (j + 1));
                        count++;
                    }
                }

                for(int k = 1; k < 10; k++)
                {
                    for(int j = 0; j < 10; j++)
                    {
                        if(((1 << j) & A[k - 1]) > 0)
                        {
                            A[k - 1] ^= (1 << j);
                            A[k] ^= (1 << j);
                            if(k < 9)
                                A[k + 1] ^= (1 << j);
                            if(j - 1 >= 0)
                                A[k] ^= (1 << (j - 1));
                            if(j + 1 < 10)
                                A[k] ^= (1 << (j + 1));
                            count ++;
                        }
                    }
                }
                if(A[9] == 0)
                    min = Math.min(min, count);
            }
            pw.print(test + " ");
            if(min > 100)
                pw.println(-1);
            else
                pw.println(min);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
