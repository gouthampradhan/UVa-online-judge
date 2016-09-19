package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * @author gouthamvidyapradhan
 * Accepted at first try 0.105 s
 *
 */
public class UnlockTheLock {

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
				if (str != null && !str.trim().equals("")) {
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
		 * Close BufferedReader
		 * 
		 * @throws Exception
		 */
		public static void close() throws Exception {
			br.close();
		}
	}
	
	private static int min[];
	private static int q[];
	private static int[] B;
	private static BitSet done = new BitSet(4098000);
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	private static int[] sF; //Size should be two always, pos 0 indicate start vertex and pos 1 indicate finish.

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		int T = 1;
		while(true)
		{
			sF = new int[2];
			int n;
			while((n = MyScanner.readInt()) == -1); //ignore blanks
			sF[0] = n;
			sF[1] = MyScanner.readInt();
			int R = MyScanner.readInt();
			B = new int[R];
			min = new int[10001];
			if(sF[0] == 0 && sF[1] == 0 && R == 0)
				break;
			for(int i=0; i< R; i++)
			{
				while((n = MyScanner.readInt()) == -1); //ignore blanks
				B[i] = n;
			}
			int min = bfs();
			pw.print("Case " + T++ + ": ");
			pw.println((min == -1)? "Permanently Locked" : min);
			done.clear(); //clear bits
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Perform bfs
	 * @return min value
	 */
	private static int bfs()
	{
		if(sF[0] == sF[1]) //start is same as finish
			return 0;
		q = new int[10001];
		int head = 0, tail = 0, start = sF[0];
		done.set(start);
		q[tail++] = start;
		min[start] = 0;
		while(head < tail)
		{
			int first = q[head++];
			for(int i=0; i<B.length; i++)
			{
				int child = (first + B[i]) % 10000; //least 4 bit addition
				if(!done.get(child))
				{
					min[child] = min[first] + 1;
					if(sF[1] == child)
						return min[child];
					q[tail++] = child;
					done.set(child);
				}
			}
		}
		return -1;
	}
}
