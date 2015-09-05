package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
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

	static class Mall
	{
		int perimeter;
		List<Integer> co;
	}
	
	static int[] q;
	static Mall m1;	
	static Mall m2;
	static int xMin;
	static int xMax;
	static int yMin;
	static int yMax;	
	static int nV = 0;
	static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	private static MyScanner scan = new MyScanner();
	private static int[][] min;
	private static final byte one = 1;
	private static final short constant = 2047;
	private static final short MAX_COORDINATE = 2001;
	private static int col;
	private static int row;

	
	public static void main(String[] args) throws Exception
	{
		while(true)
		{
			int p1 = scan.readInt();
			if(p1 == 0)
				break;
			m1 = new Mall();
			m2 = new Mall();
			readMall(m1, p1);
			
			int p2 = scan.readInt();
			readMall(m2, p2);
			
			col = ((xMax+1) - xMin);
			row = ((yMax+1) - yMin);
			nV = col * row;
			
			pw.println((m1.perimeter <= m2.perimeter) ? bfs(m1.co, m2.co) : bfs(m2.co, m1.co));
			reset();
		}
		pw.flush();
		pw.close();
		scan.close();
	}
	/**
	 * Read mall data
	 * @param m
	 * @param p
	 * @throws Exception
	 */
	static private void readMall(Mall m, int p) throws Exception
	{
		m.perimeter = p;
		m.co = new ArrayList<>();
		for(int i=0; i<p; i++)
		{
			int x = scan.readInt();
			int y = scan.readInt();
			xMin = (x < xMin) ? x : xMin;
			xMax = (x > xMax) ? x : xMax;
			yMin = (y < yMin) ? y : yMin;
			yMax = (y > yMax) ? y : yMax;
			m.co.add((x<<11) + y); //Store the combination
		}
	}
	
	static private void reset()
	{
		m1 = null;
		m2 = null;
		xMin = 0;
		xMax = 0;
		yMin = 0;
		yMax = 0;
	}
	
	/**
	 * Bfs to find the shortest distance
	 * @param s
	 * @return
	 */
	private static int bfs(List<Integer> start, List<Integer> d)
	{
		BitSet done = new BitSet(4098000);
		min = new int[col][row];
		int head = 0, tail = 0;
		q = new int[4004001];
		for(int s : start)
		{
			int sx = s >> 11;
			int sy = s & constant;
			//min[sx][sy] = 0; // initial distance set to 0
			q[tail++] = s;
			done.set(s);
			if(d.contains(s)) //If mall 1 share the same vertices as mall2. This is an impossible test case.
			{
				return min[sx][sy];
			}
		}
		while(head < tail)
		{
			int first = q[head++];
			int x = first >> 11;
			int y = first & constant;
			int leftX = x - one;
			int rightX = x + one;
			int topY = y + one;
			int downY = y - one;
			if((leftX >= xMin) && (leftX >= 0))
			{
				int child = (leftX << 11) + y;
				if(!done.get(child))
				{
					min[leftX][y] = min[x][y] + 1;
					if(d.contains(child))//Boundary of second mall reached.
						return min[leftX][y];
					q[tail++] = child; // push to queue
					done.set(child);
				}
			}
			if((rightX <= xMax) && (rightX < MAX_COORDINATE))
			{
				int child = (rightX << 11) + y;
				if(!done.get(child))
				{
					min[rightX][y] = min[x][y] + 1;
					if(d.contains(child))
						return min[rightX][y];
					q[tail++] = child;
					done.set(child);
				}
			}
			if((downY >= 0) && (downY >= yMin))
			{
				int child = (x << 11) + downY;
				if(!done.get(child))
				{
					min[x][downY] = min[x][y] + 1;
					if(d.contains(child))
						return min[x][downY];
					q[tail++] = child;
					done.set(child);
				}
			}
			if((topY <= yMax) && (topY < MAX_COORDINATE))
			{
				int child = (x << 11) + topY;
				if(!done.get(child))
				{
					min[x][topY] = min[x][y] + 1;
					if(d.contains(child))
						return min[x][topY];
					q[tail++] = child;
					done.set(child);
				}
			}
		}
		return 1; //Integer.MAX_VALUE indicating no distance.
	}
}
