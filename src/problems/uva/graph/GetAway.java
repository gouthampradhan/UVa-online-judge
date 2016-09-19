package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class GetAway {

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

	private static final int constant = 127;
	private static int[][] min;
	private static BitSet done = new BitSet(12771);
	private static Map<Integer, BitSet> restriction = new HashMap<>();
	private static Map<Integer, BitSet> monitors = new HashMap<>();
	private static int[] q;
	private static final int[] cox = {-1 , 1, 0, 0};
	private static final int[] coy = {0 , 0, -1, 1};
	private static int destination;
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	private static int row = 0;
	private static int col = 0;
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		while(true)
		{
			String str;
			str = MyScanner.readString();
			if(str == null)
				break;
			if(str.equals(""))
				continue;
			col = Integer.parseInt(str);
			if(col == -1) break;
			row = MyScanner.readInt();
			q = new int[10201];
			min = new int[101][101];
			destination = ((col - 1) << 7) + (row - 1);
			min[col-1][row-1] = Integer.MAX_VALUE; //set destination to max value
			int numRes = MyScanner.readInt();
			for(int i=0; i<numRes; i++)
			{
				int fromX = MyScanner.readInt();
				int fromY = MyScanner.readInt();
				int toX = MyScanner.readInt();
				int toY = MyScanner.readInt();
				int from = (fromX<<7) + fromY;
				BitSet restPoints;
				if(restriction.get(from) == null)
					restPoints = new BitSet(12771);
				else
					restPoints = restriction.get(from);
				restPoints.set((toX<<7)+toY);
				restriction.put((fromX<<7) + fromY, restPoints); //save the restriction
			}
			int numOfMonitors = MyScanner.readInt();
			for(int i=0; i<numOfMonitors; i++)
			{
				int seconds = MyScanner.readInt();
				int x = MyScanner.readInt();
				int y = MyScanner.readInt();
				int monitorCord = (x<<7) + y;
				BitSet waitSec = monitors.get(monitorCord);
				if(waitSec == null)
					waitSec = new BitSet(501);
				waitSec.set(seconds);
				monitors.put(monitorCord, waitSec);
			}
			pw.println(bfs());
			reset();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
		reset();
	}
	
	/**
	 * Reset variables
	 */
	private static void reset()
	{
		done.clear();
		restriction.clear();
		monitors.clear();
	}
	/**
	 * Perform a bfs search to find the shortest path.
	 * @return
	 */
	private static int bfs()
	{
		int head = 0;
		int tail = 0;
		min[0][0] = 0;
		q[tail++] = 0;
		done.set(0);
		while(head < tail)
		{
			int parent = q[head++];
			int pX = parent >> 7;
			int pY = parent & constant;
			for(int i=0; i<cox.length; i++)
			{
				int cX = pX + cox[i];
				int cY = pY + coy[i];
				if(cX >= 0 && cY >=0 && (cX <= (col-1)) && (cY <= (row-1)))
				{
					int childCordt = (cX<<7) + cY;
					if(!done.get(childCordt))
					{
						BitSet notReachable = restriction.get(parent);
						if(notReachable == null || !notReachable.get(childCordt))
						{
							BitSet monitor = monitors.get(childCordt);
							if(monitor == null)
							{
								if(childCordt == destination)
									min[cX][cY] = Math.min(min[cX][cY] , min[pX][pY] + 1);
								else
									min[cX][cY] = min[pX][pY] + 1;
							}
							else
							{
								for(int bIdx=1; bIdx < 501; bIdx++)
								{
									if(!monitor.get(min[pX][pY] + bIdx))
									{
								    	if(childCordt == destination)
								    		min[cX][cY] = Math.min(min[cX][cY] , min[pX][pY] + bIdx);
								    	else
											min[cX][cY] = min[pX][pY] + bIdx;
										break;
									}
								}
							}
							if(childCordt != destination)
							{
								q[tail++] = childCordt;
								done.set(childCordt);
							}
						}
					}
				}
			}
		}
		return min[col-1][row-1];
	}
}
