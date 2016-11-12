package problems.uva.moreadvancedtopics;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 12/11/2016.
 * Accepted 0.490 O(N !!)
 */
public class AnotherNQueenProblem
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
    private static int N, max_state, count;
    private static char[][] board;
    private static int[] bSquare;
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        int caseNr = 1;
        while(true)
        {
            N = MyScanner.readInt();
            if(N == 0)
                break;
            board = new char[N][N];
            for(int i = 0; i < N; i ++)
                board[i] = MyScanner.readLine().trim().toCharArray();
            bSquare = new int[N];
            max_state = (1 << N) - 1;
            count = 0;
            for(int i = 0; i < N; i ++)
            {
                for(int j = 0; j < N; j ++)
                {
                    if(board[j][i] == '*')
                        bSquare[i] |= (1 << j);
                }
            }
            backTrack(0, 0, 0, 0);
            pw.println("Case " + caseNr++ + ": " + count);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Backtrack to count the number of possibilities
     * @param r
     * @param ld
     * @param rd
     * @param col
     */
    private static void backTrack(int r, int ld, int rd, int col)
    {
        if(r == max_state)
            count++;
        else
        {
            int state = max_state & (~(r | ld | rd | bSquare[col]));
            while(state > 0)
            {
                int p = -state & state;

                backTrack(r | p, ((ld | p) << 1), ((rd | p) >> 1), col + 1);

                state = state - p;
            }
        }
    }
}
