package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.362s. No graph construction. Its a O(n3) algorithm but works okay since the board is small.
 *
 */
public class InjuredQueenProblem {

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
    private static String INPUT_INDEX = "123456789ABCDEF";
    private static int[] R = {0, 0, 1, 1, -1, -1};
    private static int[] C = {-1, 1, 1, -1, -1, 1};
    private static long[][] board; //board / graph
    private static boolean[][] gray; //grayed out boxes
    private static long total;
    private static int N;
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			String line = MyScanner.readLine();
			if(line == null) break;
			line = line.trim();
			if(line.equals("")) continue;
			N = line.length();
			gray = new boolean[N+1][N+1];
			board = new long[N+1][N+1];
			total = 0;
			boolean failed = false;
			char[] intArr = line.toCharArray();
			for(int i = 1; i<=N; i++)
				board[i][1] = 1; // initialize the first column of the board array to 1.
			//grey out the boxes
			for(int c = 0; c<N; c++)
			{
				if(intArr[c] == '?') continue;
				int row = (INPUT_INDEX.indexOf(intArr[c]) + 1);
				int col = c + 1;
				//check if there are two queens adjacent to each other.
				if(col < N)
				{
					if(intArr[col] != '?')
					{
						int nextRow = (INPUT_INDEX.indexOf(intArr[col]) + 1);
						if(row == nextRow || row == (nextRow + 1) || row == (nextRow - 1)) 
							failed = true;
					}
				}
				for(int r = 1; r<=N; r++)
					gray[r][col] = true;
				for(int i = 0, l = R.length; i < l; i++)
				{
					int nR = row + R[i];
					int nC = col + C[i];
					if(nR > N || nC > N || nR < 1 || nC < 1) continue;
					gray[nR][nC] = true;
				}
				gray[row][col] = false;
			}
			if(failed) //two queens can attach eachother
				pw.println(0);
			else
			{	
				countPaths();
				//print
				for(int i = 1; i<=N; i++)
					total += board[i][N];
				pw.println(total);
			}
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Count paths
	 */
	private static void countPaths()
	{
		//count paths
		for(int c = 1; c <= N; c++)
		{
			if((c + 1) > N) continue; //check for last column
			for(int r = 1; r <= N; r++)
			{
				if(gray[r][c]) continue; //if grayed out then continue;
				for(int nr = 1; nr <= N; nr++)
				{
					if(gray[nr][c+1]) continue;
					if(r == nr || r == (nr + 1) || r == (nr - 1)) continue; //check if the position can be attacked by previous queen
					board[nr][c+1] = board[nr][c+1] + board[r][c]; 
				}
			}			
		}
	}

}
