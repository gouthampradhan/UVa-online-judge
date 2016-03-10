package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 1.229 s. O(N^3) algorithm to count all the permutation and combination of throws
 *
 */
public class DartAMania {

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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int S, pCount, cCount; //permutation, combination count
    private static int[] cArray = new int[3];
    private static BitSet done = new BitSet();
    private static final int[] D = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 
    	24, 26, 27, 28, 30, 32, 33, 34, 36, 38, 39, 40, 42, 45, 48, 50, 51, 54, 57, 60};    
    private static final String NOT_POSSIBLE1 = "THE SCORE OF ", NOT_POSSIBLE2 = " CANNOT BE MADE WITH THREE DARTS.", 
    		LINE = "**********************************************************************", PERMUTATION = "NUMBER OF PERMUTATIONS THAT SCORES ", 
    				COMBINATION = "NUMBER OF COMBINATIONS THAT SCORES ", IS = " IS ", STOP = ".", END_OUTPUT = "END OF OUTPUT";
    

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			S = MyScanner.readInt();
			if(S <= 0) break;
			int N = D.length, encode;
			pCount = 0; cCount = 0;
			for(int i = 0; (i < N && D[i] <= S); i++)
				for(int j = 0; (j < N && D[j] <= S); j++)
					for(int k = 0; (k < N && D[k] <= S); k++)
					{
						if((D[i] + D[j] + D[k]) == S)
						{
							cArray[0] = D[i]; cArray[1] = D[j]; cArray[2] = D[k];
							Arrays.sort(cArray); //sort
							encode = (((cArray[0] << 6) + cArray[1]) << 6) + cArray[2];
							if(!done.get(encode))
							{
								done.set(encode);
								cCount++; 
							}
							pCount++;
						}
					}
			if(pCount == 0)
				pw.println(NOT_POSSIBLE1 + S + NOT_POSSIBLE2);
			else
			{
				pw.println(COMBINATION + S + IS + cCount + STOP);
				pw.println(PERMUTATION + S + IS + pCount + STOP);
			}
			pw.println(LINE);
			done.clear();
		}
		pw.println(END_OUTPUT);
		pw.flush(); pw.close(); MyScanner.close();
	}
}
