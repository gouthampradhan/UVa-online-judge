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
 * Accepted in first attempt 0.046 s. Easy problem similar to StickeCollectorRobot
 * @see StickerCollectorRobot
 *
 */
public class MazeTraversal {
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
				st = new StringTokenizer(str.trim());
				return st.nextToken();
			}
			return null;
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
    private static int N, M;
    private static int currentDir, currentRow, currentCol, newr, newc;     
    private static String command;
    private static final char WALL = '*';
	private static final String dirs = "NESW";
    private static final char RIGHT='R',LEFT='L', MOVE='F', QUIT = 'Q';
    private static final String BLANK = " ";
    
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		int T, count = 0;
		while((T = MyScanner.readInt()) == -1);
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1); //number of rows
			M = MyScanner.readInt(); //number of columns
			graph = new char[N][];
			for(int row=0; row<N; row++)
			{
				String line = MyScanner.readLine();
				if(line.equals("")) line = BLANK;
				graph[row] = line.toCharArray();
			}
			while((currentRow = MyScanner.readInt()) == -1); //read the initial position
			currentCol = MyScanner.readInt();
			currentRow -= 1; currentCol -= 1; currentDir = 0;
			StringBuffer buf = new StringBuffer();
			String in;
			while(true)
			{
				in = MyScanner.readString();
				buf.append(in);
				if(in.indexOf(QUIT) != -1)
					break;
			}
			command = buf.toString();
			maneuver();
			if(count != 0)
				pw.println();
			pw.println((currentRow+1) + BLANK + (currentCol+1) + BLANK + dirs.charAt(currentDir));
			count++;
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Method performs the maneuver
	 */
	private static void maneuver()
	{
		int length = command.length();
		for(int i=0; i<length; i++)
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
					 if(newr >= 0 && newc >= 0 && newr < N && newc < M && (graph[newr][newc] != WALL))
					 {
						 currentRow = newr;
						 currentCol = newc;
					 }
					 break;
					 
				case QUIT:
					 return;
			}
		}
	}
}
