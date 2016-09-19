package problems.uva.graph;

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
 * Accepted third attempt 0.455 s. Got WA 3 times, seems to be a problem with max row number. Problem description says max row of 30, but had to extend
 * the max row count to 40 (btw, I think the max row count is 32).
 * Interesting floodfill problem, fill with color only if the border is !Blank
 *
 */
public class ContourPainting {

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
    
    private static char[][] grid = new char[40][90];
    private static final String START = "*", LINE_ID = "_", EMPTY = "";
    private static final char BLANK = ' ', COLOUR = '#';
    private static String PATTERN = "\\s+$", LINE; //Patterns to remove trailing spaces
    private static final int R[] = {0, -1, 0, 1}; //{E, N, W, S}
    private static final int C[] = {1, 0, -1, 0};
    private static int sr, sc; //start row, start column
    private static int T, MAX_ROW, MAX_COL;
    private static BitSet done = new BitSet(2511);
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			int pos = 0;
			MAX_ROW = 40; MAX_COL = 0; //reset max_col to 0
			for(int i=0; i<MAX_ROW; i++)
				Arrays.fill(grid[i], BLANK); //fill with spaces
			while(true)
			{
				String line = MyScanner.readLine();
				if(line.equals(EMPTY)) {pos++; continue;}
				if(line.contains(LINE_ID)) {LINE = line; break;} //save the line and break
				if(line.contains(START)) {sr = pos; sc = line.indexOf(START);}
				char[] inChar = line.toCharArray();
				int l = line.length();
				for(int i=0; i<l; i++)
					grid[pos][i] = inChar[i]; //overwrite the grid spaces with input chars
				MAX_COL = Math.max(l, MAX_COL); //save the max column length
				pos++;
			}
			MAX_ROW = pos; //save the max row length
			grid[sr][sc] = BLANK; color(sr, sc);
			for(int i=0; i<MAX_ROW; i++) //print the grid
				pw.println(String.valueOf(grid[i]).replaceFirst(PATTERN, EMPTY)); //remove only the trailing spaces, NOT leading.
			pw.println(LINE);
			done.clear(); //clear done
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Method to floodFill and color the contour
	 * @param r
	 * @param c
	 * @return
	 */
	private static void color(int r, int c)
	{
		done.set((r << 7) + c); //mark this as finished
		for(int i = 0; i < 4; i++)
		{
			int newR = r+R[i]; int newC = c+C[i];
			if(newR < 0 || newC < 0 || newR >= MAX_ROW || newC > MAX_COL || done.get((newR << 7) + newC)) continue;
			if(grid[newR][newC] != BLANK)
				grid[r][c] = COLOUR; //color the parent
			else
				color(newR, newC);
		}
	}
}
