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
 * 
 * Accepted 0.153 s. My initial solution with recursive dfs approach failed, repeatedly got RE but I wasn't able to figure out the root cause.
 * After googling I found a much better approach using 'NESO' string and {-1,0,1,0} for coordinates which is much faster and avoids redundant code.
 *
 */
public class StickerCollectorRobot {
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
		 * Read string
		 * 
		 * @return
		 * @throws Exception
		 */
		public static String readString() throws Exception {
			if (st != null && st.hasMoreTokens()) {
				return st.nextToken();
			}
			String str = br.readLine();
			if (str != null) 
			{
				String s = str.trim();
				if (s.equals(""))
					return "";
				st = new StringTokenizer(s);
				return st.nextToken();
			}
			return null;
		}

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
		 * Parse to integer
		 * @param in
		 * @return integer value
		 */
		private static int parseInt(String in)
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
	
    private static int[] dr = {-1,0,1,0};//NESW
    private static int[] dc = {0,1,0,-1};  
    private static char[][] graph;
    private static int N, M, S, stickerCount, index;
    private static int currentDir, currentRow, currentCol, newr, newc;     
    private static String command;
    private static final char PILLER = '#';
    private static final char STICKER = '*';
    private static final char EMPTY = '.';
	private static final String dirs = "NLSO";//NESW
    private static final char RIGHT='D',LEFT='E', MOVE='F';
    
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			M = MyScanner.readInt();
			S = MyScanner.readInt();
			if(N == 0) break;
			graph = new char[N][M];
			stickerCount = 0;
			boolean found = false;
			for(int row=0; row<N; row++)
			{
				String line;
				while((line = MyScanner.readString()).equals(""));
				graph[row] = line.toCharArray();
				int col;
				if(!found)
				if(((col = line.indexOf('N')) != -1) || ((col = line.indexOf('L')) != -1) || 
						((col = line.indexOf('S')) != -1) || ((col = line.indexOf('O')) != -1))
				{
					found = true;
					index = dirs.indexOf(graph[row][col]);
					currentDir = index;
					currentRow = row;
					currentCol = col;
				}
			}
			while((command = MyScanner.readString()).equals(""));
			collectSticker();
			pw.println(stickerCount);
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Method performs the maneuver
	 */
	private static void collectSticker()
	{
		for(int i=0; i<S; i++)
		{
			char ch = command.charAt(i);
			switch(ch)
			{
				case RIGHT:
					 currentDir = (currentDir + 1) % 4;
					 break;
					 
				case LEFT:
					 currentDir = (currentDir + 3) % 4;
					 break;

				case MOVE:
					 newr = currentRow + dr[currentDir];
					 newc = currentCol + dc[currentDir];
					 char next;
					 if(newr >= 0 && newc >= 0 && newr < N && newc < M && ((next = graph[newr][newc]) != PILLER))
					 {
						 if(next == STICKER)
						 {
							 stickerCount++;
							 graph[newr][newc] = EMPTY; //I think this is not necessary
						 }
						 currentRow = newr;
						 currentCol = newc;
					 }
					 break;
			}
		}
	}
}
