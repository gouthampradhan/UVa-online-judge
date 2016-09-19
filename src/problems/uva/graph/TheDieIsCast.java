package problems.uva.graph;

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
 * Accepted first attempt 0.079 s. Tricky flood fill, floodFill dice and if a dot is found then, floodFill dot and again floodFill each colored dot.
 *
 */
public class TheDieIsCast 
{
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
    
    private static char[][] grid;
    private static final char BACKGROUND = '.';
    private static final char DIE = '*';
    private static final char DOT = 'X';
    private static final String BLANK = " ";
    private static final String THROW = "Throw ";
    private static final int R[] = {0, -1, 0, 1}; //{E, N, W, S}
    private static final int C[] = {1, 0, -1, 0};
    private static int[] dotsOnDice = new int[1000];
    private static int caseCount = 0, W, H; //H rows, W columns
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((W = MyScanner.readInt()) == -1); 
			if(W == 0) break;
			H = MyScanner.readInt();
			grid = new char[H][W];
			Arrays.fill(dotsOnDice, Integer.MAX_VALUE);
			int pos = 0;
			for(int i=0; i<H; i++)
				grid[i] = MyScanner.readLine().trim().toCharArray();
			for(int i=0; i<H; i++)
				for(int j=0; j<W; j++)
					if(grid[i][j] == BACKGROUND) continue;
					else{ dotsOnDice[pos++] = fillDie(i, j); }
			Arrays.sort(dotsOnDice); //Sort the array in ascending order
			pw.println(THROW + ++caseCount);
			pw.print(dotsOnDice[0]);
			for(int i=1, l = dotsOnDice.length; i<=l; i++)
			{
				if(dotsOnDice[i] == Integer.MAX_VALUE) break;
				pw.print(BLANK + dotsOnDice[i]);
			}
			pw.println();
			pw.println();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Method to floodFill all the pixels of a die
	 * @param r
	 * @param c
	 * @return
	 */
	private static int fillDie(int r, int c)
	{
		int count = 0;
		if(grid[r][c] == DOT)
			{fillDots(r, c); count++;}
		grid[r][c] = BACKGROUND; //mark this as finished
		for(int i = 0; i < 4; i++)
		{
			int newR = r+R[i]; int newC = c+C[i];
			if(newR < 0 || newC < 0 || newR >= H || newC >= W || grid[newR][newC] == BACKGROUND) continue;
			if(grid[newR][newC] == DIE)
				count += fillDie(newR, newC);
			else {count++; fillDots(newR, newC); count += fillDie(newR, newC);} //its DOT
		}
		return count;
	}
	
	/**
	 * Method to floodFill all the pixels of a dot in a die
	 * @param r
	 * @param c
	 */
	private static void fillDots(int r, int c)
	{
		grid[r][c] = DIE; //mark this as finished
		for(int i = 0; i < 4; i++)
		{
			int newR = r+R[i]; int newC = c+C[i];
			if(newR < 0 || newC < 0 || newR >= H || newC >= W || grid[newR][newC] == BACKGROUND || grid[newR][newC] == DIE) continue;
				fillDots(newR, newC);
		}
	}
}
