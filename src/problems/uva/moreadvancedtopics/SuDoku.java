package problems.uva.moreadvancedtopics;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 19/11/2016.
 * Accepted 0.050s
 */
public class SuDoku
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
    private static int N;
    private static int[][] A, S;
    private static int[] C, R;

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        int count = 0;
        while(true)
        {
            while((N = MyScanner.readInt()) == -1);
            if(N == -2) break;
            A = new int[N * N][N * N];
            C = new int[N * N];
            R = new int[N * N];
            S = new int[N][N];
            if(count++ != 0)
                pw.println();
            int n;
            for(int i = 0; i < N * N; i ++)
            {
                for(int j = 0; j < N * N; j ++)
                {
                    n = MyScanner.readInt();
                    A[i][j] = n;
                    if(n != 0)
                    {
                        R[i] |= (1 << n);
                        C[j] |= (1 << n);
                        S[i / N][j / N] |= (1 << n);
                    }
                }
            }
            if(!backTrack(0 , 0))
                pw.println("NO SOLUTION");
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Print result
     */
    private static void print()
    {
        for(int i = 0; i < N * N; i ++)
        {
            for(int j = 0; j < N * N; j ++)
            {
                if(j != 0)
                    pw.print(" ");
                pw.print(A[i][j]);
            }
            pw.println();
        }
    }

    /**
     * Backtrack to fill the matrix
     */
    private static boolean backTrack(int i, int j)
    {
        if(i >= (N * N))
        {
            print();
            return true;
        }
        if(A[i][j] > 0)
        {
            if(j == ((N * N) - 1))
                return (backTrack(i + 1, 0));
            else
                return (backTrack(i, j + 1));
        }
        else
        {
            int result = R[i] | C[j] | S[i / N][j / N];
            for(int k = 1, l = R.length; k <= l; k ++)
            {
                if((result & (1 << k)) == 0)
                {
                    A[i][j] = k;
                    R[i] |= (1 << k);
                    C[j] |= (1 << k);
                    S[i / N][j / N] |= (1 << k);
                    if(j == ((N * N) - 1))
                    {
                        if(backTrack(i + 1, 0))
                            return true;
                    }
                    else
                    {
                        if(backTrack(i, j + 1))
                            return true;
                    }
                    //reset
                    A[i][j] = 0;
                    R[i] &= ~(1 << k);
                    C[j] &= ~(1 << k);
                    S[i / N][j / N] &= ~(1 << k);
                }
            }
        }
        return false;
    }
}
