package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.142. Simple straight forward DP problem similar to @see InjuredQueenProblem. Important to perfom MOD % after every addition to calculate new
 * max value. 
 *
 */
public class Checkers {

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
    private static int[] R1 = {-1, -1};
    private static int[] C1 = {1, -1};
    private static int[] R2 = {-2, -2};
    private static int[] C2 = {2, -2};
    private static int[][] max; //max paths
    private static char[][] board; //board / graph
    private static boolean[][] done; //grayed out boxes
    private static int total;
    private static int N, wx, wy, top, count;
    private static final String W = "W", CASE = "Case ", COLON = ": ";
    private static final char B = 'B';
    private static final int CONSTANT = 1000007;
    private static Position[] toposort;

    /**
     *
     * @author gouthamvidyapradhan
     * Class to store position
     *
     */
    private static class Position
    {
    	int x;
    	int y;
    	public Position(int y, int x) {
    		this.x = x;
    		this.y = y;
		}
    }
    
    /**
     * Main class
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		int T = MyScanner.readInt();
		count = 0;
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			max = new int[N][N]; board = new char[N][N]; done = new boolean[N][N];
			toposort = new Position[N*N];
			top = -1; total = 0; //initialize the total to 0
			for(int i = 0; i < N; i++)
			{
				String line = MyScanner.readLine().trim();
				board[i] = line.toCharArray();
				if(line.contains(W))
				{
					//save the position of start vertex 
					wx = line.indexOf(W); //column
					wy = i; //row
					max[wy][wx] = 1; //initialize the starting vertex to 1.
				}
			}
			dfs(wy, wx); //toposort
			//count paths
			countPaths();
			for(int i = 0; i<N; i++)
				total = (total + max[0][i]) % CONSTANT;
			pw.print(CASE + ++count + COLON);
			pw.println(total);
		}
		pw.flush();pw.close();MyScanner.close();
	}
	
	
	/**
	 * Dfs to toposort vertices
	 * @param px position x
	 * @param py position y
	 */
	private static void dfs(int py, int px)
	{
		done[py][px] = true;
		for(int i = 0, l = R1.length; i < l; i++)
		{
			int ny = py + R1[i];
			int nx = px + C1[i];
			if(ny >= N || ny < 0 || nx >= N || nx < 0) continue; //boundary check
			if(!done[ny][nx])
			{
				try
				{
				if(board[ny][nx] != B)
					dfs(ny, nx);
				else
				{
					ny = py + R2[i];
					nx = px + C2[i];
					if(ny >= N || ny < 0 || nx >= N || nx < 0) continue; //boundary check
					if(!done[ny][nx])
					{
						if(board[ny][nx] != B)
							dfs(ny, nx);
					}
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		Position pos = new Position(py, px);
		toposort[++top] = pos;
	}
	
	/**
	 * Count paths
	 */
	private static void countPaths()
	{
		//count paths
		for(int i = top; i >= 0; i--)
		{
			Position pos = toposort[i];
			for(int j = 0, l = R1.length; j < l; j++)
			{
				int ny = pos.y + R1[j];
				int nx = pos.x + C1[j];
				if(ny >= N || ny < 0 || nx >= N || nx < 0) continue; //boundary check
				if(board[ny][nx] != B)
					max[ny][nx] = (max[ny][nx] + max[pos.y][pos.x]) % CONSTANT;
				else
				{
					ny = pos.y + R2[j];
					nx = pos.x + C2[j];
					if(ny >= N || ny < 0 || nx >= N || nx < 0) continue; //boundary check
					if(board[ny][nx] != B)
						max[ny][nx] = (max[ny][nx] + max[pos.y][pos.x]) % CONSTANT;
				}
			}
		}
	}
}
