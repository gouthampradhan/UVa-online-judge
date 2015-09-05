package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.StringTokenizer;

public class MallMania 
{
	/**
	 * Scanner class
	 * @author gouthamvidyapradhan
	 *
	 */
	static class MyScanner 
	{
		/**
		 * Buffered reader
		 */
		private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st;
		
		/**
		 * Read integer
		 * @return
		 * @throws Exception
		 */
		public int readInt() throws Exception
		{
			if(st != null && st.hasMoreTokens())
			{
				return Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			return Integer.parseInt(st.nextToken());
		}
		
		/**
		 * Read string
		 * @return
		 * @throws Exception
		 */
		public String readString() throws Exception
		{
			if(st != null && st.hasMoreTokens())
			{
				return st.nextToken();
			}
			String str = br.readLine();
			if(str != null && !str.equals(""))
			{
				st = new StringTokenizer(str);
				return st.nextToken();
			}				
			return "";
		}
		
		/**
		 * Close BufferedReader
		 * @throws Exception
		 */
		public void close() throws Exception
		{
			br.close();
		}
	}

	static int[] q;
	static int nV = 0;
	static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	private static MyScanner scan = new MyScanner();
	private static int[][] min;
	private static final byte one = 1;
	private static final short constant = 2047;
	private static final short MAX_COORDINATE = 2001;
	private static int tail = 0;
	private static int head = 0;
	private static BitSet destination = new BitSet(4098000);
	private static BitSet done = new BitSet(4098000);
	public static void main(String[] args) throws Exception
	{
		while(true) 
		{
			int p1 = scan.readInt();
			if(p1 == 0)
				break;
			q = new int[4004001];
			head = 0; 
			tail = 0;
			for(int i=0; i<p1; i++)
			{
				int x = scan.readInt();
				int y = scan.readInt();
				int co = (x<<11) + y;
				q[tail++] = co;
				done.set(co);
			}

			int p2 = scan.readInt();
			for(int i=0; i<p2; i++)
			{
				int x = scan.readInt();
				int y = scan.readInt();
				destination.set((x<<11) + y);
			}
			pw.println(bfs());
			destination.clear();
			done.clear();
		}
		pw.flush();
		pw.close();
		scan.close();
	}

	/**
	 * Bfs to find the shortest distance
	 * @param s
	 * @return
	 */
	private static int bfs()
	{
		min = new int[2001][2001];
		while(head < tail)
		{
			int first = q[head++];
			int x = first >> 11;
			int y = first & constant;
			int leftX = x - one;
			int rightX = x + one;
			int topY = y + one;
			int downY = y - one;
			if(leftX >= 0)
			{
				int child = (leftX << 11) + y;
				if(!done.get(child))
				{
					min[leftX][y] = min[x][y] + 1;
					if(destination.get(child))//Boundary of second mall reached.
						return min[leftX][y];
					q[tail++] = child; // push to queue
					done.set(child);
				}
			}
			if(rightX < MAX_COORDINATE)
			{
				int child = (rightX << 11) + y;
				if(!done.get(child))
				{
					min[rightX][y] = min[x][y] + 1;
					if(destination.get(child))
						return min[rightX][y];
					q[tail++] = child;
					done.set(child);
				}
			}
			if(downY >= 0)
			{
				int child = (x << 11) + downY;
				if(!done.get(child))
				{
					min[x][downY] = min[x][y] + 1;
					if(destination.get(child))
						return min[x][downY];
					q[tail++] = child;
					done.set(child);
				}
			}
			if(topY < MAX_COORDINATE)
			{
				int child = (x << 11) + topY;
				if(!done.get(child))
				{
					min[x][topY] = min[x][y] + 1;
					if(destination.get(child))
						return min[x][topY];
					q[tail++] = child;
					done.set(child);
				}
			}
		}
		return 1; 
	}
}
