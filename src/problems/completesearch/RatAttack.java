package problems.completesearch;

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
 * Accepted 0.538 s. O (N ^ 3) brute force.
 *
 */
public class RatAttack {

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
    private static int D, N, T;
    private static final String BLANK = " ";
    private static List<Nest> nests;
    private static int[][] K;
    /**
     * Rat nest class
     * @author gouthamvidyapradhan
     *
     */
    private static class Nest 
    {
    	int x, y, p;
    	Nest(int x, int y, int p)
    	{
    		this.x = x;
    		this.y = y;
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
			while((D = MyScanner.readInt()) == -1);
			N = MyScanner.readInt();
			K = new int[1030][1030];
			Nest max = new Nest(0, 0, Integer.MIN_VALUE);
			nests = new ArrayList<>();
			for(int i = 0; i < N; i++)
				nests.add(new Nest(MyScanner.readInt(), MyScanner.readInt(), MyScanner.readInt())); //row, column, population
			for(Nest n : nests)
			{
				int y = n.y; int x = n.x;
				for(int i = (y - D); i <= (y + D) && i <= 1024; i++)
				{
					if(i < 0 ) continue;
					for(int j = (x - D); j <= (x + D) && j <= 1024; j++)
					{
						if(j < 0) continue;
						K[i][j] += n.p;
						if(K[i][j] > max.p)
						{
							max.p = K[i][j];
							max.x = j;
							max.y = i;
						}
					}
				}
			}
			pw.println(max.x + BLANK + max.y + BLANK + max.p);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
