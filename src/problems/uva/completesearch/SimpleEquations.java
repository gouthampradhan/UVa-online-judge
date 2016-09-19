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
 * Accepted 0.102s. O (N^3) solution. More optimization possible.
 *
 */
public class SimpleEquations {

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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 500000)); //set the buffer too high. 
    private static int A, B, C, T;
    private static final String NOT_POSSIBLE = "No solution.", BLANK = " ";
    
    /**
     * Main method
     * @param args
     */
	
    public static void main(String[] args) throws Exception 
    {
    	T = MyScanner.readInt();
    	while(T-- > 0)
    	{
    		A = MyScanner.readInt();
    		B = MyScanner.readInt();
    		C = MyScanner.readInt();
    		boolean solution = false;
    		for(int i = -100; i <= 100 && !solution; i++)
    		{
    			for(int j = i + 1; j <= 100 && !solution; j++)
    			{
    				for(int k = j + 1; k <= 100; k++)
    				{
    					if(i == j || j == k || i == k) continue;
    					if((i * k * j) == B && (i + j + k) == A && (i * i + j * j + k * k) == C)
    					{
    						pw.println(i + BLANK + j + BLANK + k);
    						solution = true;
    						break;
    					}
    				}
    			}	
    		}
    		if(!solution)
    			pw.println(NOT_POSSIBLE);
    	}
    	pw.flush(); pw.close(); MyScanner.close();
	}
}
