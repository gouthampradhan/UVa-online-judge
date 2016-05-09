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
 * Accepted 109ms
 *
 */
public class SeatingOnBus
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
                if (str != null && !str.trim().equals("")) {
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
    
    private static int N, M;
    private static int[][] B;
    private static final int[] O = {1, 0, 2, 3}; 
    private static final int[] E1 = {0, 3};
    private static final int[] E2 = {1, 2};
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000)); //set the buffer too high.

    /**
     * 
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		N = MyScanner.readInt();
		M = MyScanner.readInt();
		B = new int[N][4];
		int next = 1;
		boolean done = false;
		for(int i = 0; i < N; i++)
		{
			if(done) break;
			for(int j = 0; j < 2; j++)
			{
				B[i][E1[j]] = next++;
				if(next > M)
				{
					done = true;
					break;
				}
			}
		}
		if(!done)
		{
			for(int i = 0; i < N; i++)
			{
				if(done) break;
				for(int j = 0; j < 2; j++)
				{
					B[i][E2[j]] = next++;
					if(next > M)
					{
						done = true;
						break;
					}
				}
			}
		}
		int count = 0, temp;
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				temp = B[i][O[j]];
				if(temp != 0)
				{
					if(count++ != 0)
						pw.print(" ");
					pw.print(temp);
				}
			}
		}
		pw.println();
		pw.flush();
		pw.close();
		MyScanner.close();
	}
}
