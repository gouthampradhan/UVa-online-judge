package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class MiceAndMaze {

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
		 * Read short
		 * @return
		 * @throws Exception
		 */
		public short readShort() throws Exception
		{
			if(st != null && st.hasMoreTokens())
			{
				return Short.parseShort(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			return Short.parseShort(st.nextToken());
		}

		/**
		 * Read byte
		 * @return
		 * @throws Exception
		 */
		public byte readByte() throws Exception
		{
			if(st != null && st.hasMoreTokens())
			{
				return Byte.parseByte(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			return Byte.parseByte(st.nextToken());
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

	/**
	 * Cell class to store cell info
	 * @author gouthamvidyapradhan
	 *
	 */
	static class Cell
	{
		byte index;
		int seconds;
	}
	
	/**
	 * Priority Queue
	 */
	private static Queue<Cell> pq = new PriorityQueue<Cell>(120, new CellComparator());
	
	/**
	 * PrintWriter
	 */
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	
	/**
	 * AdjList
	 */
	private static List<List<Cell>> graph = new ArrayList<>();
	
	/**
	 * Array to hold the min distance
	 */
	static int[] minDistance;

	/**
	 * Comparator
	 * @author gouthamvidyapradhan
	 *
	 */
	static class CellComparator implements Comparator<Cell>
	{
		@Override
		public int compare(Cell o1, Cell o2) 
		{
			return (o1.seconds < o2.seconds) ? -1 : (o1.seconds == o2.seconds) ? 0 :  1;		
		}
		
	}
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		MyScanner scan = new MyScanner();
		int tC = scan.readInt();
		byte nC;
		byte finish;
		int time;
		short nE;
		for(int i=1; i<=tC; i++)
		{
			byte count = 1;
			scan.readString(); // ignore the blank line
			nC = scan.readByte(); //number of cells
			if(nC == 0)
				continue;
			if(nC == 1)
			{
				pw.println(1);
				continue;
			}
			finish = scan.readByte();//finish cell
			time = scan.readInt();//time limit
			nE = scan.readShort();
			//Initialize graph
			for(int v = 0; v<=nC; v++)
			{
				List<Cell> list = new ArrayList<Cell>();
				graph.add(v, list);
			}
			for(short e = 0; e<nE; e++)
			{
				byte from = scan.readByte();
				Cell to = new Cell();
				to.index = scan.readByte();
				to.seconds = scan.readByte();
				graph.get(from).add(to);
			}
			for(byte c=1; c<=nC; c++)
			{
				if(c!=finish)
				{
					if(findShPath(c, finish, graph, nC) <= time)
						count++;
				}
			}
			pw.println(count);
			if(i != tC)
				pw.println();
			graph.clear();
		}
		pw.close();
		scan.close();
	}
	
	/**
	 * Dijktra's algorithm to find shortest path
	 * @param from
	 * @param to
	 * @return
	 */
	static private int findShPath(byte from, byte to, List<List<Cell>> graph, int nR)
	{
		minDistance = new int[nR+1];
		for(int i=0; i<minDistance.length; i++)
		{
			minDistance[i] = Integer.MAX_VALUE;
		}
		Cell start = new Cell();
		start.index = from;
		start.seconds = 0;
		minDistance[start.index] = 0;
		pq.add(start);
		while(!pq.isEmpty())
		{
			Cell parent = pq.remove();
			if(parent.seconds != minDistance[parent.index])
				continue;
			List<Cell> e = graph.get(parent.index);
			for(Cell child : e)
			{
				if((child.seconds + parent.seconds) < minDistance[child.index])
				{
					//Update the min distance
					minDistance[child.index] = child.seconds + parent.seconds;
					Cell temp = new Cell();
					temp.index = child.index;
					temp.seconds = child.seconds + parent.seconds;
					//temp.p = parent.i;
					pq.add(temp);
				}
			}
		}
		return minDistance[to];
	}
}
