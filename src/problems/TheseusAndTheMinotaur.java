package problems;

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
 * Accepted first attempt 0.452 s. Simple problem but the input description is a bit ambiguous - for example it does not mention the limit of variable K.
 * However, after reading one of the posts, I just ignore the input limit of variable K and the code runs successfully without any problem. 
 *
 */
public class TheseusAndTheMinotaur {
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
    
    private static final String CAVERN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String[] graph = new String[26];
    private static boolean[] done = new boolean[26];
    private static String M, T;
    private static int count, K, tPos; //theseus position
    private static final String BLANK = " ";
    private static final String SEMICOLON = ";";
    private static final String FULLSTOP = ".";
    private static final String COLON = ":";
    private static final String SLASH = "/";
    private static final String HASH = "#";
    private static final String EMPTY = "";
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			String in = MyScanner.readLine();
			if(in.equals(HASH)) break;
			Arrays.fill(done, false); Arrays.fill(graph, EMPTY);
			count = 0;
			StringTokenizer tokens1 = new StringTokenizer(in, FULLSTOP);
			String part1 = tokens1.nextToken().trim();
			String part2 = tokens1.nextToken().trim();
			StringTokenizer tokens2 = new StringTokenizer(part2, BLANK);
			M = tokens2.nextToken().trim(); T = tokens2.nextToken().trim(); K = MyScanner.parseInt(tokens2.nextToken().trim()); //Store the initial positon of Minotaur and Theseus
			StringTokenizer tokens3 = new StringTokenizer(part1, SEMICOLON);
			while(tokens3.hasMoreTokens())
			{
				StringTokenizer tokens4 = new StringTokenizer(tokens3.nextToken(), COLON);
				String from = tokens4.nextToken().trim();
				String to = EMPTY;
				if(tokens4.hasMoreTokens()) 
					to = tokens4.nextToken();
				int i = CAVERN.indexOf(from);
				graph[i] = to;
			}
			tPos = CAVERN.indexOf(T); //store Theseus's current position
			dfs(CAVERN.indexOf(M));
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Dfs to find the trapped cavern
	 * @param i
	 */
	private static void dfs(int i)
	{
		while(true)
		{
			char childrenArr[] = graph[i].toCharArray();
			int j = 0, pos = 0; boolean found = false;
			for (int l = childrenArr.length; j <  l; j++)
			{
				pos = CAVERN.indexOf(childrenArr[j]);
				if(!done[pos] && pos != tPos)
				{
					tPos = i; //update Theseus position
					if(++count == K) {done[i] = true; pw.print(CAVERN.charAt(i) + BLANK); count = 0;} //mark this vertex as done and reset the count.
					found = true;  break; //break here don't have to continue with other children
				}
			}
			if(found) i = pos;
			else 
			{
				pw.println(SLASH + CAVERN.charAt(i));
				break;
			}
		}
	}
}
