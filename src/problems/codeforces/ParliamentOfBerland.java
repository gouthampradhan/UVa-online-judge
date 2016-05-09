package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 *
 */
public class ParliamentOfBerland {

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
    private static int N, a, b;
    private static int[][] P;
    private static final String BLANK = " ";
    /**
     * 
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		N = MyScanner.readInt();
		a = MyScanner.readInt();
		b = MyScanner.readInt();
		int count = 1;
		P = new int[a][b];
		if(N > (a * b)) pw.println(-1);
		else
		{
			for(int i = 0; (i < a) && (count - 1) < N; i++)
			{
				if((i % 2) == 0)
				{
					for(int j = 0; (j < b && (count - 1) < N); j++)
						P[i][j] = count++; //place a parliamentarian
				}
				else
				{
					for(int j = (b - 1); (j >= 0 && (count - 1) < N); j--)
						P[i][j] = count++;
				}
			}
			for(int i = 0; i < a; i++)
			{
				for(int j = 0; j < b; j++)
				{
					if(j > 0)
						pw.print(BLANK);
					pw.print(P[i][j]);
				}
				pw.println();
			}
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
