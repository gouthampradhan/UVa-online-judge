package problems.uva.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.092 s. Simple backtracking solution. O(2^n) solution works okay since the input size is very small.
 *
 */
public class CD {

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
    private static int L, N, sum, temp;
    private static int[] A, a;
    private static boolean found;
    private static String result;
    private static final String BLANK = " ", SUM = "sum:";
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((L = MyScanner.readInt()) == -1); //length
			if(L == -2) break;
			N = MyScanner.readInt(); //no of tracks
			A = new int[N];
			a = new int[N];
			sum = 0;
			result = "";
			found = false;
			for(int i = 0; i < N; i++)
				A[i] = MyScanner.readInt();
			backTrack(0, sum, 0);
			pw.println(result + SUM + sum);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Backtrack to find the right combination
	 * @param p current position
	 * @param s sum
	 * @param next new start point of the array
	 */
	private static void backTrack(int p, int s, int next)
	{
		if(!found)
		{
			for(int i = next; i < N; i++)
			{
				temp = s + A[i];
				if(temp <= L)
				{
					a[p] = A[i];
					if(temp >= sum) //if the new sum is gt the old sum only then form the result string
					{
						sum = temp;
						String tempRes = "";
						for(int j = 0; j <= p; j++)
						{
							tempRes += a[j];
							tempRes += BLANK;
							result = tempRes; //form the result
						}
						if(temp == L)
						{
							found = true;
							break;
						}
					}
					backTrack(p + 1, temp, i + 1);
				}
				if(found) break;
			}
		}
	}
}
