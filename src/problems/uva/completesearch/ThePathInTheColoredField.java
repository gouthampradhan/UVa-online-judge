package problems.uva.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.129 s. Simple brute force and calculation using Manhattan distance.
 *
 */
public class ThePathInTheColoredField {

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
    private static int M, max, min;
    private static List<Color> O, T;
    /**
     * Color class
     * @author gouthamvidyapradhan
     *
     */
    private static class Color
    {
    	int y, x;
    	Color(int y, int x)
    	{
    		this.y = y; //row
    		this.x = x; //col
    	}
    }
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			M = MyScanner.readInt();
			if(M == -2) break;
			int n; max = Integer.MIN_VALUE;
			O = new ArrayList<>();
			T = new ArrayList<>();
			for(int i = 0; i< M; i++)
			{
				char[] in = MyScanner.readLine().toCharArray();
				for(int j = 0; j < M; j++)
				{
					n = Character.getNumericValue(in[j]);
					Color c = new Color(i, j);
					if(n == 1)
						O.add(c);
					else if(n == 3)
						T.add(c);
				}
			}
			
			//Manhattan distance
			for(Color o : O)
			{
				min = Integer.MAX_VALUE;
				for(Color t : T)
				{
					min = Math.min(min, Math.abs(t.x - o.x) + Math.abs(t.y - o.y));
				}
				max = Math.max(max, min);
			}
			pw.println(max);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
