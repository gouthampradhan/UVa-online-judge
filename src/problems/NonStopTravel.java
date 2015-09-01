package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author gouthamvidyapradhan
 * 
 * Uva 341
 *
 */
class NonStopTravel 
{
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
	 * 
	 * @author gouthamvidyapradhan
	 * 
	 * Class I (Intersection) to store the index and seconds
	 *
	 */
	static class I
	{
		//Intersection
		byte i;
		//Seconds
		int s;
	}
	
	/**
	 * AdjList
	 */
	static List<List<I>> aL;
	
	/**
	 * Intersection List (edge list)
	 */
	static List<I> iL;
	
	/**
	 * Priority queue
	 */
	static java.util.Queue<I> pq = new java.util.PriorityQueue<I>(20, new IComparator());
	
	/**
	 * Printwriter to write output
	 */
	static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	
	/**
	 * Array to hold the min distance
	 */
	static int[] minDistance;
	
	/**
	 * Array to hold the min distance path.
	 */
	static byte[] minPath;
	
	/**
	 * number of intersection
	 */
	static byte ni;
	
	
	static byte[] path;
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Comparator
	 *
	 */
	static class IComparator implements Comparator<I> 
	{

		@Override
		public int compare(I o1, I o2) 
		{
			return (o1.s < o2.s) ? -1 : (o1.s == o2.s) ? 0 :  1;
		}
	}
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		MyScanner scan = new MyScanner();
		String str;
		int iCase = 1;
		while(!(str = scan.readString()).equals("0"))
		{
			//Create a AdjList of size ni+1 (pos 0 contains null always)
			if(str.equals(""))
				continue;
			ni = Byte.parseByte(str);
			aL = new ArrayList<List<I>>(ni+1);
			aL.add(0, null);
			for(byte i=1; i<=ni; i++)
			{
				byte e = scan.readByte();
				//Create a edge list of size e
				iL = new ArrayList<I>(e);
				for(byte ii=0; ii<e; ii++)
				{
					I iSec = new I();
					iSec.i = scan.readByte();
					iSec.s = scan.readInt();
					iL.add(iSec);
				}
				aL.add(i, iL);
			}
			byte from = scan.readByte();
			byte to = scan.readByte();
			int distance = 0;
			pw.print("Case " + (iCase++) + ": Path = " + from);
			if(from != to)
			{
				distance = findShPath(from, to);
				byte i = to;
				path = new byte[minPath.length];
				byte ii = 0;
				path[0] = to;
				while((minPath[i] != 0) && (minPath[i] != from))
				{
					path[++ii] = minPath[i];
					i = minPath[i];
				}
				for(int e = ii; e>=0; e--)
					pw.print(" " + path[e]);
			}
			pw.println("; " + distance + " second delay");
		}
		pw.flush();
		pw.close();
		scan.close();
	}
	
	/**
	 * Dijktra's algorithm to find shortest path
	 * @param from
	 * @param to
	 * @return
	 */
	static private int findShPath(byte from, byte to)
	{
		minDistance = new int[ni+1];
		minPath = new byte[ni+1];
		for(int i=0; i<minDistance.length; i++)
		{
			minDistance[i] = Integer.MAX_VALUE;
		}
		I start = new I();
		start.i = from;
		start.s = 0;
		minDistance[start.i] = 0;
		minPath[start.i] = 0; //No parent
		pq.add(start);
		while(!pq.isEmpty())
		{
			I parent = pq.remove();
			if(parent.s != minDistance[parent.i])
				continue;
			List<I> e = aL.get(parent.i);
			for(I child : e)
			{
				if((child.s + parent.s) < minDistance[child.i])
				{
					//Update the min distance
					minDistance[child.i] = child.s + parent.s;
					I temp = new I();
					temp.i = child.i;
					temp.s = child.s + parent.s;
					//temp.p = parent.i;
					pq.add(temp);
					//Keep track of the path
					minPath[child.i] = parent.i;
				}
			}
		}
		return minDistance[to];
	}
}
