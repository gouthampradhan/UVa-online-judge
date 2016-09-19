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
 * Accepted : 0.159 s. O(N ^5) algorithm. Problem statement was difficult to understand, what should be output was not clear. Also, struggled to come up with
 * an algorithm for showing all combinations of office placements
 *
 */
public class CitizenAttentionOffices {

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
    private static int T, N, min;
    private static int[] O = new int[5];
    private static Area[] A;
    private static final String BLANK = " ";
    
    /**
     * Area class
     * @author gouthamvidyapradhan
     *
     */
    private static class Area
    {
    	int x, y, p; //coorinates and population
    	Area(int y, int x, int p)
    	{
    		this.y = y;
    		this.x = x; 
    		this.p = p;
    	}
    }
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			A = new Area[N];
			int sum, o1, o2, o3, o4, o5; min = Integer.MAX_VALUE;
			for(int i = 0; i<N; i++)
				A[i] = new Area(MyScanner.readInt(), MyScanner.readInt(), MyScanner.readInt());
			for(int a = 0; a <= (25 - 5); a++)
				for(int b = a + 1; b <= (25 - 4); b++)
					for(int c = b + 1; c <= (25 - 3); c++)
						for(int d = c + 1; d <= (25 - 2); d++)
							for(int e = d + 1; e <= (25 - 1); e++)
							{
								sum = 0;
								for(Area ai : A)
								{
									o1 = (Math.abs(ai.x - a % 5) + Math.abs(ai.y - a / 5)) * ai.p;
									o2 = (Math.abs(ai.x - b % 5) + Math.abs(ai.y - b / 5)) * ai.p;
									o3 = (Math.abs(ai.x - c % 5) + Math.abs(ai.y - c / 5)) * ai.p;
									o4 = (Math.abs(ai.x - d % 5) + Math.abs(ai.y - d / 5)) * ai.p;
									o5 = (Math.abs(ai.x - e % 5) + Math.abs(ai.y - e / 5)) * ai.p;
									sum += Math.min(Math.min(Math.min(Math.min(o1, o2), o3), o4), o5);
								}
								if(sum < min)
								{
									min = sum;
									O[0] = a; O[1] = b; O[2] = c; O[3] = d; O[4] = e;  
								}
								
							}
			for(int i = 0; i < 5; i++)
			{
				if(i != 0)
					pw.print(BLANK);
				pw.print(O[i]);
			}
			pw.println();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
