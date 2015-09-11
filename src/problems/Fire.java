package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * 
 * The solution was initially not accepted due to a runtime error. The reason for runtime error was the size of the min[][] array and the q[] which was 
 * initialized to a very small size.
 * Solution was finally accepted after increase in the size. Its now proven that the concept with bitwise << and & to combine numbers is simply a hit !!
 * Accepted 0.395
 *
 */
class Main {
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
					return Integer.parseInt(st.nextToken());
				}
				String str = br.readLine();
				if (str != null && !str.equals("")) {
					st = new StringTokenizer(str);
					return Integer.parseInt(st.nextToken());
				}
			} catch (IOException e) {
				close();
				return -1;
			}
			return -1;
		}
		
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
			if (str != null) {
				if (str.equals(""))
					return "";
				st = new StringTokenizer(str);
				return st.nextToken();
			}
			return null;
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

	private static BitSet notAllowed = new BitSet(4098000);
	private static int min[][];
	private static int q[];
	private static final int constant = 1023;
	private static final int[] cox = { -1, 1, 0, 0 };
	private static final int[] coy = { 0, 0, -1, 1 };
	private static int head, tail = 0;
	private static int R, C = 0;
	private static BitSet done = new BitSet(4098000);
	private static int fireCount = 0;
	private static int tempCount = 0;
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		int T;
		T = MyScanner.readInt();
		while (T-- > 0) {
			q = new int[1004004];
			R = MyScanner.readInt();
			C = MyScanner.readInt();
			min = new int[R+1][C+1];
			int joe = 0;
			boolean joeInBorder = false;
			for (int y = 0; y < R; y++) 
			{
				String in = MyScanner.readString();
				char[] chars = in.toCharArray();
				for (int x = 0; x < C; x++) 
				{
					switch (chars[x]) 
					{
						case '#':
							notAllowed.set((x << 10) + y);
							break;
	
						case 'J':
							if(x == 0 || (x == (C-1)) || (y == 0) || (y == (R-1)))
								joeInBorder = true;
							else
							{
								joe = (((x << 10) + y) << 1) + 1; // Last bit of 1 signify coordinates of joe
								min[y][x] = 1;
							}
							break;
	
						case 'F':
							int fire = (x << 10) + y;
							q[tail++] = (fire << 1); // Last bit of 0 signify coordinates of Fire
							notAllowed.set(fire);
							fireCount++;
							break;
					}
				}
			}
			q[tail++] = joe; // push joe to the rear of queue.
			if(joeInBorder)
				pw.println(1);
			else
			{
				int distance = bfs();
				if(distance == -1)
					pw.println("IMPOSSIBLE");
				else
					pw.println(distance);
			}
			reset();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Reset variables
	 */
	private static void reset()
	{
		notAllowed.clear();
		done.clear();
		head = tail = 0;
		fireCount = tempCount = 0;
	}
	
	/**
	 * Perform a bfs search
	 * 
	 * @return
	 */
	private static int bfs() 
	{
		boolean safe = true; //A flag which determines if we could stop after all the joe elements in the queue are exausted.
		while (head < tail) 
		{
			int first = q[head++];
		    int identity = first & 1;
			first >>= 1;
			if (identity == 0) // Fire
			{
				if(!safe)
					return -1;
				int pX = first >> 10;
				int pY = first & constant;
				for (int co = 0; co < cox.length; co++) 
				{
					int cX = pX + cox[co];
					int cY = pY + coy[co];
					if (cX >= 0 && cX <= (C - 1) && cY >= 0
							&& cY <= (R - 1)) //boundary condition
					{
						int temp = (cX << 10) + cY;
						if (!notAllowed.get(temp)) 
						{
							q[tail++] = temp << 1; // enqueue
							notAllowed.set(temp);
							tempCount++;
						}
					}
				}
				if(--fireCount == 0)
				{
					safe = false; // This is the last fire element so, reset the flag to false.
					fireCount = tempCount;
					tempCount = 0;
				}
			} 
			else 
			{
				int pX = (first >> 10);
				int pY = first & constant;
				for (int co = 0; co < cox.length; co++) 
				{
					int cX = pX + cox[co];
					int cY = pY + coy[co];
					int temp = (cX << 10) + cY;
					if (!notAllowed.get(temp) && !done.get(temp)) 
					{
						if (cX >= 0 && cX <= (C - 1) && cY >= 0
								&& cY <= (R - 1)) //boundary condition
						{
							if (cX == 0 || cX == (C - 1) || cY == 0
									|| cY == (R - 1)) // exit condition
								return min[pY][pX] + 1;
							else 
							{
								min[cY][cX] = min[pY][pX] + 1;
								q[tail++] = (temp << 1) + 1;
								done.set(temp);// Done dont visit again
								safe = true;
							}
						}
					}
				}
			}
		}
		return -1;
	}
}
