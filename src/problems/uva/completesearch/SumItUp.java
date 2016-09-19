package problems.uva.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.060s. Simple backtracking O(2^n) algorithm with hashing to avoid duplicates.
 *
 */
public class SumItUp {

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
    private static int N, T;
    private static int[] A, S;
    private static boolean found;
    private static Set<String> done;
    private static final String SUM = "Sums of ", PLUS = "+", NONE = "NONE", COLON = ":";

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception
	{
		while(true)
		{
			T = MyScanner.readInt();
			if(T == 0) break;
			N = MyScanner.readInt();
			A = new int[N];
			S = new int[N];
			found = false;
			done = new HashSet<>();
			for(int i = 0; i < N; i++)
				A[i] = MyScanner.readInt();
			pw.println(SUM + T + COLON);
			backTrack(S, 0, 0);
			if(!found)
				pw.println(NONE);
		}
		pw.flush(); pw.close(); MyScanner.close(); 
	}
	
	/**
	 * Backtrack to check all combinations of sums
	 * @param S
	 * @param pos
	 */
	private static void backTrack(int[] S, int pos, int next)
	{
		if(next == N) return;
		for(int i = next; i < N; i++)
		{
			int sum = 0;
			S[pos] = A[i];
			for(int j = 0; j <= pos; j++)
				sum += S[j];
			if(sum == T)
			{
				found = true;
				String temp = "";
				for(int j = 0; j <= pos; j++)
					temp += S[j];
				if(!done.contains(temp))
				{
					done.add(temp);
					for(int j = 0; j <= pos; j++)
					{
						if(j != 0)
							pw.print(PLUS);
						pw.print(S[j]);
					}
					pw.println();
				}
				continue;
			}
			else if (sum < T)
				backTrack(S, pos + 1, i + 1);
		}
	}
}
