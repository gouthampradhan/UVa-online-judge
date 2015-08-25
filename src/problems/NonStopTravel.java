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
			return new StringTokenizer(br.readLine()).nextToken();
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
		byte s;
		//Parent
		byte p;
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
	
	static I[] minDistance;
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
		while(!(str = scan.readString()).equals("0"))
		{
			//Create a AdjList of size ni+1 (pos 0 contains null always)
			if(str.equals('\n'))
				continue;
			byte ni = Byte.parseByte(str);
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
					iSec.s = scan.readByte();
					iL.add(iSec);
				}
				aL.add(i, iL);
				byte from = scan.readByte();
				byte to = scan.readByte();
			}
		}
	}
	
	/**
	 * Dijktra's algorithm to find shortest path
	 * @param from
	 * @param to
	 * @return
	 */
	private byte findShPath(byte from, byte to)
	{
		if(from == to)
			return 0;
		I start = new I();
		start.i = from;
		start.s = 0;
		pq.add(start);
		while(!pq.isEmpty())
		{
			I iSec = pq.remove();
			List<I> e = aL.get(iSec.i);
			for(I i : e)
			{
				
			}
		}
		return 0;
	}
}
