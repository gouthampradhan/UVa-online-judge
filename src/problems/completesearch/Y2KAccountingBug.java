package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.050 s. Problem statement is difficult to understand but the solution is simple. Backtracking and pruning O(2^12 * 8 * 5). 
 *
 */
public class Y2KAccountingBug {

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
    
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000)); //set the buffer too high.
    private static int result;
    private static int[] A, P = new int[12];
    private static final String DEFICIT = "Deficit";
    
    /**
     * Main method
     * 
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			int s;
			A = new int[2];
			while((s = MyScanner.readInt()) == -1);
			if(s == -2) break;
			int d = MyScanner.readInt();
			A[0] = s; A[1] = (d * -1);
			result = Integer.MIN_VALUE;
			backTrack(0, 0);
			if(result > 0)
				pw.println(result);
			else
				pw.println(DEFICIT);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * 
	 * @param p
	 * @param sum
	 */
	private static void backTrack(int p, int sum)
	{
		if(p == 12)
		{
			if(isValid())
				result = Math.max(result, sum);
			return;
		}
		for(int i = 0; i < 2; i++)
		{
			P[p] = A[i];
			backTrack(p + 1, sum + A[i]);
		}
	}
	
	/**
	 * Sum of the consecutive 5 numbers
	 * @return
	 */
	private static boolean isValid()
	{
		for(int i = 0; i < 8; i++)
		{
			int sum = 0;
			for(int j = i; j < (i + 5); j++)
				sum += P[j];
			if(sum > 0) return false;
		}
		return true;
	}
}
