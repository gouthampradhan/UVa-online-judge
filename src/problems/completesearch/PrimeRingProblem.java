package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.550s. Make sure to flush the output for input 16 since the output is too large.
 * Simple backtracking O(8!) 
 *
 */
public class PrimeRingProblem {

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
    private static int N, count;
    private static int[] R;
    private static List<Integer> I;
    private static final int[] P = {2, 3, 5};
    private static final String CASE = "Case ", BLANK = " ", COLON = ":";
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		count = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == -2) break;
			I = new ArrayList<>();
			R = new int[N];
			for(int i = 2; i <= N; i++)
				I.add(i);
			Arrays.fill(R, 0);
			R[0] = 1;
			if(count != 0)
				pw.println();
			pw.println(CASE + ++count + COLON);
			backTrack(R, 1);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Backtrack to check for prime
	 * @param ring
	 * @param pos
	 */
	private static void backTrack(int[] R, int pos)
	{
		if(pos == N)
		{
			int last = R[pos - 1];
			if(isPrime(last + 1)) //important check if the last + first is a prime
			{
				for(int i = 0; i < N; i++)
				{
					if(i != 0)
						pw.print(BLANK);
					pw.print(R[i]);
				}
				pw.println();
			}
			return;
		}
		for(int i : I)
		{
			if(ringContains(i, pos)) continue;
			int prev = R[pos - 1];
			if(isPrime(prev + i))
			{
				R[pos] = i;
				backTrack(R, pos + 1);
			}
		}
	}
	
	/**
	 * Check if ring already contains
	 * @param i
	 * @param pos
	 * @return
	 */
	private static boolean ringContains(int i, int pos)
	{
		for(int p = 0; p < pos; p++)
			if(R[p] == i) return true;
		return false;
	}
	/**
	 * Check for prime
	 * @param p
	 */
	private static boolean isPrime(int n)
	{
		for(int p: P)
		{
			if(p != n)
			{
				if((n % p) == 0)
					return false;
			}
		}
		return true;
	}
}
