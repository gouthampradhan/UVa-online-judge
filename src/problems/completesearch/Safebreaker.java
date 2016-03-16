package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Safebreaker {

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
    private static int T, G;
    private static List<Integer> N = new ArrayList<>();
    private static List<Guess> guessList;
    private static Set<Integer> set;
    private static final int NUM = 10000;
    
    /**
     * Guess class
     * @author gouthamvidyapradhan
     *
     */
    private static class Guess 
    {
    	int[] code;
    	int cl, wl; //correct position, wrong position
    	Guess(int[] code, int cl, int wl)
    	{
    		this.code = code;
    		this.cl = cl;
    		this.wl = wl;
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
			G = MyScanner.readInt();
			guessList = new ArrayList<Guess>();
			set = new HashSet<Integer>();
			for(int i = 0; i<G; i++)
			{
				String line = MyScanner.readLine();
				StringTokenizer st = new StringTokenizer(line);
				char[] chars = String.valueOf(st.nextToken()).toCharArray();
				int[] num = {chars[0] - '0', chars[1] - '0', chars[2] - '0', chars[3] - '0'};
				StringTokenizer st1 = new StringTokenizer(st.nextToken(), "/");
				int cl = Integer.parseInt(st1.nextToken());
				int wl = Integer.parseInt(st1.nextToken());
				guessList.add(new Guess(num, cl, wl));
				if(cl == 0 && wl == 0)
				{
					//numbers to be ignored
					for(int n : num)
						set.add(n);
				}
			}
			for(int i = 0; i < 10; i++)
				if(!set.contains(i))
					N.add(i); //numbers to be used for iteration
			set.clear(); // reuse the same set.
			for(int a = 0, l = N.size(); a < l; a++)
				for(int b = a; b < l; b++)
					for(int c = b; c < l; c++)
						for(int d = c; d < l; d++)
							for(Guess g : guessList)
							{
								//check for each guess and add to the set.
							}
			//if size of set > 1 then indeterminate, if empty then impossible if contains 1 then print the secret code.
		}
	}
}
