package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Rebus {

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
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static long readLong() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return Long.parseLong(st.nextToken());
                }
                String str = br.readLine();
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return Long.parseLong(st.nextToken());
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
    private static int N;
    private static String line, lhs;
    private static int[] P, M;
    private static final String PLUS = "+", MINUS = "-", 
    		BLANK = " ", EQUAL = "=", POSSIBLE = "Possible", IMPOSSIBLE = "Impossible";
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		line = MyScanner.readLine().trim();
		StringTokenizer st = new StringTokenizer(line, EQUAL);
		lhs = st.nextToken().trim();
		N = MyScanner.parseInt(st.nextToken().trim());
		int l = lhs.length();
		int plus = 1, minus = 0, sum = 0;
		boolean done = false;
		for(int i = 0; i < l; i++)
		{
			switch (lhs.charAt(i)) 
			{
				case '+':
					plus++;
					break;
	
				case '-':
					minus++;
					break;
					
				default:
					break;
			}
		}
		P = new int[plus];
		M = new int[minus];
		int max = (N * plus) - minus;
		int min = (N * minus * -1) + plus;
		if(N > max || N < min)
			pw.println(IMPOSSIBLE);
		else
		{
			for(int i = 0, le = P.length; i < le; i++)
			{
				P[i] = 1;
				sum += 1;
			}
			for(int i = 0, le = M.length; i < le; i++)
			{
				M[i] = 1;
				sum -= 1;
			}
			if(sum < N) //need increment
			{
				for(int i = 0, le = P.length; i < le && !done; i++)
				{
					int temp = (sum - P[i]) + N;
					if(temp == N)
					{
						P[i] = N;
						sum = temp;
						done = true;
						break;
					}
					else if(temp < N)
					{
						P[i] = N;
						sum = temp;
					}
					else
					{
						for(int j = 1; j <= N; j++)
						{
							temp = (sum - P[i]) + j;
							if(temp == N)
							{
								sum = temp;
								P[i] = j;
								done = true;
								break;
							}
						}
					}
				}
			}
			else if(sum > N)
			{
				for(int i = 0, le = M.length; i < le && !done; i++)
				{
					int temp = (sum + M[i]) - N;
					if(temp == N)
					{
						M[i] = N;
						sum = temp;
						done = true;
						break;
					}
					else if(temp > N)
					{
						M[i] = N;
						sum = temp;
					}
					else
					{
						for(int j = 1; j <= N; j++)
						{
							temp = (sum + M[i]) - j;
							if(temp == N)
							{
								sum = temp;
								M[i] = j;
								done = true;
								break;
							}
						}
					}
				}
			}
			print();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Print values
	 */
	private static void print()
	{
		pw.println(POSSIBLE);
		line = line.replaceFirst("\\?", String.valueOf(P[0]));
		for(int i = 1, l = P.length; i < l; i++)
			line = line.replaceFirst("\\+ \\?", PLUS + BLANK + String.valueOf(P[i]));
		for(int i = 0, l = M.length; i < l; i++)
			line = line.replaceFirst("\\- \\?", MINUS + BLANK + String.valueOf(M[i]));
		pw.println(line);
	}
}
