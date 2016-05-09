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
 * Accepted 0.300 s. Recursive backtracking O(8 ^ 8) solution. Was a bit tricky to form a solution considering the initial position of queens, but later
 * discovered that we can create all possible combinations of queen placements and just calculate and maintain a minimum number of moves for each combination. 
 *
 */
public class BackToThe8Queens {

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
    private static int[] I, P;
    private static int moves, count;
    private static final String CASE = "Case ", COLON = ": ";
    
    /**
     * 
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		count = 0;
		while(true)
		{
			I = new int[8];
			P = new int[8];
			int a = MyScanner.readInt();
			if(a == -2) break;
			I[0] = a;
			moves = Integer.MAX_VALUE;
			for(int i = 1; i < 8; i++)
				I[i] = MyScanner.readInt();
			backTrack(0);
			pw.println(CASE + ++count + COLON + moves);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Backtrack to check all possible combinations of queen placements.
	 * @param p
	 */
	private static void backTrack(int p)
	{
		if(p == 8) //reached
		{
			count();
			return;
		}
		for(int i = 1; i <= 8; i++)
		{
			if(isValid(i, p))
			{
				P[p] = i; //place the queen
				backTrack(p + 1);
			}
		}
	}
	
	/**
	 * Count moves and maintain a minimum
	 */
	private static void count()
	{
		int temp = 0;
		for(int i = 0; i < 8 ; i++)
		{
			if(I[i] != P[i])
				temp++;
		}
		moves = Math.min(moves, temp);
	}
	
	/**
	 * Check if the position is valid
	 * @param r
	 * @param c
	 * @return
	 */
	private static boolean isValid(int r, int c)
	{
		for(int i = 0; i < c; i++)
		{
			if(P[i] == r) return false; //occupies same row
			else if(Math.abs(i - c) == Math.abs(r - P[i])) return false;
		}
		return true;
	}
}
