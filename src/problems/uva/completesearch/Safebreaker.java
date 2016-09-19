package problems.uva.completesearch;

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

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.722 s.
 * My Algorithm : O(N^4) brute-force find all possible combinations of 4-digit numbers and then, check against each guess if it satisfies each of the guess
 * if successful then save this as the result. If there are more then 1 possible results then its indeterminate, if none found then impossible.
 *
 */
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
    private static String result;
    private static final String INDETERMINATE = "indeterminate", IMPOSSIBLE = "impossible";
    
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
			result = null;
			for(int i = 0; i<G; i++)
			{
				String line = MyScanner.readLine();
				StringTokenizer st = new StringTokenizer(line);
				char[] chars = String.valueOf(st.nextToken()).toCharArray();
				int[] num = {chars[0] - '0', chars[1] - '0', chars[2] - '0', chars[3] - '0'};
				StringTokenizer st1 = new StringTokenizer(st.nextToken(), "/");
				int cl = MyScanner.parseInt(st1.nextToken().trim());
				int wl = MyScanner.parseInt(st1.nextToken().trim());
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
			boolean indeterminate = false;
			for(int a = 0, l = N.size(); a < l && !indeterminate; a++)
				for(int b = 0; b < l && !indeterminate; b++)
					for(int c = 0; c < l && !indeterminate; c++)
						for(int d = 0; d < l && !indeterminate; d++)
						{
							boolean found = true;
							int[] num = new int[4];
							num[0] = N.get(a); num[1] = N.get(b); num[2] = N.get(c); num[3] = N.get(d);
							for(Guess g : guessList)
							{
								int cl = 0, wl = 0;
								int[] code = g.code;
								List<Integer> temp1 = new ArrayList<>();
								List<Integer> temp2 = new ArrayList<>();
								if(num[0] == code[0]) cl++;
								else {temp1.add(num[0]); temp2.add(code[0]);}
								if(num[1] == code[1]) cl++;
								else {temp1.add(num[1]); temp2.add(code[1]);}
								if(num[2] == code[2]) cl++;
								else {temp1.add(num[2]); temp2.add(code[2]);}
								if(num[3] == code[3]) cl++;
								else {temp1.add(num[3]); temp2.add(code[3]);}
								for(int i = 0, l1 = temp1.size(); i < l1; i++)
								{
									for(int j = 0, l2 = temp2.size(); j < l2; j++)
									{
										if(temp1.get(i) == temp2.get(j))
										{
											wl++; 
											temp2.remove(j);
											break;
										}
									}
								}
								if(cl != g.cl || wl != g.wl)
								{
									found = false;
									break;
								}
								//check for each guess and add to the set.
							}
							if(found)
							{
								if(result == null)
									result = "" + num[0] + num[1] + num[2] + num[3];
								else
								{
									indeterminate = true;
									break;
								}
							}
						}
			if(indeterminate) pw.println(INDETERMINATE);
			else if(result == null) pw.println(IMPOSSIBLE);
			else pw.println(result);
			N.clear();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
