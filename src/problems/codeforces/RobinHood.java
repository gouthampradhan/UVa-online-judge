package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 256 ms. Very good problem with binary search. 
 * Good learning for me as part of binary search problems.
 * Worst case complexity. O(N log 10 ^ 9)
 *
 */
public class RobinHood {

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
    private static int[] T;
    private static int N, K;
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args)  throws Exception 
	{
		N = MyScanner.readInt();
		K = MyScanner.readInt();
		T = new int[N];
		for(int i = 0; i < N; i++)
			T[i] = MyScanner.readInt();
		int low = 0, high = 0;
		int l = 0, h = (int)1e9 + 1, m;
		while(l <= h)
		{
			m = (l + h) / 2;
			if(checkLow(m))
			{
				low = m;
				l = m + 1;
			}
			else h = m - 1;
		}
		l = 0; h = (int)1e9 + 1;
		while(l <= h)
		{
			m = (l + h) / 2;
			if(checkHigh(m))
			{
				high = m;
				h = m - 1;
			}
			else l = m + 1;
		}
		if(high > low)
			pw.println(high - low);
		else 
		{
			//given K is very high. So check if we can share the coins completely to maximum share possible
			long temp = 0;
			for(int i = 0; i < N; i++)
				temp += T[i];
			pw.println(((temp % N) == 0) ? 0 : 1);
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Check low possible
	 * @param p
	 * @return
	 */
	private static boolean checkLow(int p)
	{
		long sum = 0;
		for(int i = 0; i < N; i++)
		{
			if(p > T[i])
				sum += p - T[i];
		}
		return (sum <= K);
	}
	
	/**
	 * Check high possible
	 * @param p
	 * @return
	 */
	private static boolean checkHigh(int p)
	{
		long sum = 0;
		for(int i = 0; i < N; i++)
		{
			if(T[i] > p)
				sum += T[i] - p;
		}
		return (sum <= K);
	}

}
