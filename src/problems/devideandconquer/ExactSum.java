package problems.devideandconquer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.150 s. Simple binary search. My Algorithm O(N x log N)
 *
 */
public class ExactSum {

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
    private static int N, M, low, high, min;
    private static int[] I;
    private static final String PETER = "Peter should buy books whose prices are ", AND = " and ", STOP = ".";
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args)  throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == -2) break;
			I = new int[N];
			low = 0; high = 0;
			min = Integer.MAX_VALUE;
			for(int i = 0; i < N; i++)
				I[i] = MyScanner.readInt();
			M = MyScanner.readInt();
			Arrays.sort(I);
			for(int i = 0; i < N; i++)
				bs(I[i], i + 1, N - 1);
			pw.println(PETER + low + AND + high + STOP);
			pw.println();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Binary search the optimal value
	 */
	private static void bs(int curr, int l, int h)
	{
		int m, midVal;
		while(l < h - 1)
		{
			m = (l + h) / 2;
			midVal = I[m];
			if((midVal + curr) == M)
			{
				if(Math.abs(curr - midVal) < min)
				{
					min = Math.abs(curr - midVal);
					low = Math.min(curr, midVal);
					high = Math.max(curr, midVal);
				}
				return;
			}
			else if((midVal + curr) < M)
				l = m;
			else
				h = m;
		}
		if(l == h - 1 || l == h)
		{
			if((I[l] + curr) == M)
			{
				if(Math.abs(curr - I[l]) < min)
				{
					min = Math.abs(curr - I[l]);
					low = Math.min(curr, I[l]);
					high = Math.max(curr, I[l]);
				}
			}
			if((I[h] + curr) == M)
			{
				if(Math.abs(curr - I[h]) < min)
				{
					min = Math.abs(curr - I[h]);
					low = Math.min(curr, I[h]);
					high = Math.max(curr, I[h]);
				}
			}
		}
	}
}
