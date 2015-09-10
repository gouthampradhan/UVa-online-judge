package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.StringTokenizer;

public class Fire {
	/**
	 * Scanner class
	 * @author gouthamvidyapradhan
	 */
	static class MyScanner 
	{
		/**
		 * Buffered reader
		 */
		private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		private static StringTokenizer st;
		
		/**
		 * Read integer
		 * @return
		 * @throws Exception
		 */
		public static int readInt() throws Exception
		{
			try
			{
				if(st != null && st.hasMoreTokens())
				{
					return Integer.parseInt(st.nextToken());
				}
				String str = br.readLine();
				if(str != null && !str.equals(""))
				{
					st = new StringTokenizer(str);
					return Integer.parseInt(st.nextToken());
				}
			}
			catch(IOException e)
			{
				close();
				return -1;
			}
			return -1;
		}
		
		/**
		 * Read string
		 * @return
		 * @throws Exception
		 */
		public static String readString() throws Exception
		{
			if(st != null && st.hasMoreTokens())
			{
				return st.nextToken();
			}
			String str = br.readLine();
			if(str != null)
			{
				if(str.equals(""))
					return "";
				st = new StringTokenizer(str);
				return st.nextToken();
			}				
			return null;
		}
		
		/**
		 * Close BufferedReader
		 * @throws Exception
		 */
		public static void close() throws Exception
		{
			br.close();
		}
	}
	
	private static BitSet notAllowed = new BitSet();
	private static int min[][];
	private static int q[];
	private static final int constant = 1023;
	private static final int[] cox = {-1 , 1, 0, 0};
	private static final int[] coy = {0 , 0, -1, 1};
	private static int head, tail = 0;
	private static int R, C = 0;

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		int T;
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			R = MyScanner.readInt();
			C = MyScanner.readInt();
			min = new int[R+1][C+1];
			int joe = 0;
			for(int y = 0; y< R; y++)
			{
				for(int x = 0; x< C; x++)
				{
					String in = MyScanner.readString();
					switch(in)
					{
						case "#":
								notAllowed.set((x<<10) + y);
								break;
						
						case "J":
								joe = (((x<<10) + y) << 1) + 1; //Last bit of 1 signify coordinates of Joe
								min[x][y] = 1;
								break;

						case "F":
								int fire = (x<<10) + y;
								q[tail++] = (fire << 1); //Last bit of 1 signify coordinates of Fire
								notAllowed.set(fire);
								break;
					}
				}
			}
			q[tail++] = joe; //push joe to the rear of queue.
		}
	}
	
	private static int bfs()
	{
		while(head > tail)
		{
			int first = q[head++];
			int identity = Integer.lowestOneBit(first);
			if(identity == 1) //Joe
			{
				first = first >> 1;
				int pX = (first >> 10);
				int pY = first & constant;
				for(int i=0; i<cox.length; i++)
				{
					int cX = pX + cox[i];
					int cY = pY + coy[i];
					int temp = (cX<<10) + cY;
					if(!notAllowed.get(temp))
					{
						if(cX == 0 || cX == (C-1) || cY == 0 || cY == (R-1))
							return min[pX][pY] + 1;
						else
						{
							min[cX][cY] = min[pX][pY] + 1;
							q[tail++] = (temp << 1) + 1;
						}
					}
				}
			}
			else
			{
				first = first >> 1;
				int pX = (first >> 10);
				int pY = first & constant;
				for(int i=0; i<cox.length; i++)
				{
					int cX = pX + cox[i];
					int cY = pY + coy[i];
				}
			}
		}
		return -1;
	}
}
