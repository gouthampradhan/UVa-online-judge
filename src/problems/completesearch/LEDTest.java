package problems.completesearch;

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
 * Accepted 0.060 s. Simple recursive backtracking problem, initially a but hard to understand and work out the complexity of the algorithm.
 *
 */
public class LEDTest {

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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
    private static String[] input;
    private static int N;
    private static final String[] LED = {"YYYYYYN", "NYYNNNN", "YYNYYNY", "YYYYNNYY", 
    	"NYYNNYY", "YNYYNYY", "YNYYYYY", "YYYNNNN", "YYYYYYY", "YYYYNYY"};
    private static final String MATCH = "MATCH", MISMATCH = "MISMATCH";

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == 0) break;
			input = new String[N];
			for(int i = 0; i < N; i++)
				input[i] = MyScanner.readLine().trim();
			boolean match = false;
			boolean[] burned = new boolean[7];
			for(int i = 9; i + 1 >= N; i--)
			{
				Arrays.fill(burned, false);
				if(bt(i, 0, burned))
				{
					match = true;
					break;
				}
			}
			pw.println(match ? MATCH : MISMATCH); 
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Backtrack to find the match
	 * @param pos
	 * @param i
	 * @return
	 */
	private static boolean bt(int pos, int i, boolean[] burned)
	{
		if(i == N)
			return true;
		String led = LED[pos];
		String in = input[i];
		for(int j = 0; j < 7; j++)
		{
			if(burned[j])
			{
				if(in.charAt(j) == 'N')
					continue;
				else 
					return false;
			}
			if(in.charAt(j) == led.charAt(j))
				continue;
			else if(in.charAt(j) == 'N' && led.charAt(j) == 'Y')
			{
				burned[j] = true;
				continue;
			}
			else 
				return false;
		}
		return bt(pos - 1, i + 1, burned);
	}
}
