package problems.uva.devideandconquer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.220 s. Simple binary search the answer problem.
 * Algorithm complexity O(log N) * 10 ^ 5
 *
 */
public class TheMonkeyAndTheOiledBamboo {

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
    private static int N, T;
    private static int[] R;
    private static final String CASE = "Case ", COLON = ":", BLANK = " ";
    
    enum Status
    {
    	LOW,
    	CORRECT;
    }
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		int count = 0;
		while(T-- > 0)
		{
			N = MyScanner.readInt();
			int l, h, m, min = Integer.MAX_VALUE;
			R = new int[N];
			for(int i = 0; i < N; i++)
				R[i] = MyScanner.readInt();
			l = R[0]; h = R[N - 1];
			if(N == 1)
				min = R[0];
			else
			{
				while(l < h - 1)
				{
					m = (l + h) / 2;
					Status status = check(m);
					if(status == Status.LOW)
						l = m;
					else
					{
						h = m;
						min = Math.min(min, m);
					}
				}
				if(l == h - 1)
				{
					Status statusL = check(l);
					if(statusL == Status.CORRECT)
						min = Math.min(min, l);
					Status statusH = check(h);
					if(statusH == Status.CORRECT)
						min = Math.min(min, h);
				}
			}
			pw.println(CASE + ++count + COLON + BLANK + min);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Check if the given value satisfies
	 * @param e
	 * @return
	 */
	private static Status check(int e)
	{
		int prev = 0, jump;
		for(int i = 0; i < N; i++)
		{
			jump = R[i] - prev;
			prev = R[i];
			if(jump == e)
				e--;
			else if(jump > e)
				return Status.LOW;
		}
		return Status.CORRECT;
	}
}
