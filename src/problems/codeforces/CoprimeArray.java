package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 108ms
 *
 */
public class CoprimeArray {

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
    
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000)); //set the buffer too high.
    private static int N;
    private static int[] A;
    private static List<Long> result = new ArrayList<Long>(2000);
    /**
     * 
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		N = MyScanner.readInt();
		A = new int[N];
		for(int i = 0; i < N ; i++)
			A[i] = MyScanner.readInt();
		result.add((long)A[0]);
		for(int i = 0; i < N ; i++)
		{
			int j = i + 1;
			if(j == N) break;
			BigInteger b1 = BigInteger.valueOf(A[i]);
			BigInteger b2 = BigInteger.valueOf(A[j]);
			if(b1.gcd(b2).longValue() == 1)
				result.add(b2.longValue());
			else
			{
				result.add(1L);
				result.add(b2.longValue());
			}
		}
		pw.println(result.size() - N);
		for(int i = 0, l = result.size(); i < l; i++)
		{
			if(i != 0)
				pw.print(" ");
			pw.print(result.get(i));
		}
		pw.println();
		pw.flush(); pw.close(); MyScanner.close();
	}
}
