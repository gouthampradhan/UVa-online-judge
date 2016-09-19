package problems.uva.graph;

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
 * Accepted with 0.358 s. Received a RE initially where the problem was not considering any blanks in between the test case input and the actual test data. This
 * was however not mentioned in the initial problem description. But, I think as a good practice it is always better to consider multiple blank lines in
 * between test cases or any blank likes between the test case number and actual test data.
 *
 */
public class PlayingWithWheels {

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

	private static int min[];
	private static int q[];
	private static final int constant = 15;
	private static final int[] I = { -1, 1, 0, 0, 0, 0, 0, 0};
	private static final int[] II = { 0, 0, -1, 1, 0, 0, 0, 0};
	private static final int[] III = { 0, 0, 0, 0, -1, 1, 0, 0};
	private static final int[] IV = { 0, 0, 0, 0, 0, 0, -1, 1};
	private static BitSet done = new BitSet(4098000);
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	private static int[] sF; //Size should be two always, pos 0 indicate start vertex and pos 1 indicate finish.
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		int T = MyScanner.readInt();
		while(T-- > 0)
		{
			sF = new int[2];
			min = new int[40000];
			for(int i=0; i<2; i++)
			{
				int[] dig = new int[4];
				for(int d=0; d<4; d++)
				{
					int n;
					while((n = MyScanner.readInt()) == -1);
						dig[d] = n;
				}
				sF[i] = (((((dig[0] << 4) + dig[1]) << 4) + dig[2]) << 4) + dig[3];
				if(i == 0)
					min[sF[0]] = 0;
			}
			int R = MyScanner.readInt(); //number of restrictions
			while(R-- > 0)
			{
				int[] dig = new int[4];
				for(int d=0; d<4; d++)
				{
					dig[d] = MyScanner.readInt();
				}
				done.set((((((dig[0] << 4) + dig[1]) << 4) + dig[2]) << 4) + dig[3]); //Restricted vertices
			}
			pw.println(bfs());
			done.clear(); //clear bits
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Peform bfs
	 * @return
	 */
	private static int bfs()
	{
		if(sF[0] == sF[1]) //start is same as finish
			return 0;
		int start = sF[0];
		done.set(start);
		int head = 0, tail = 0;
		q = new int[10001];
		q[tail++] = start;
		while(head < tail)
		{
			int first = q[head++];
			int d = first & constant;
			int c = (first >> 4) & constant;
			int b = (first >> 8) & constant;
			int a = (first >> 12);
			int newA = a, newB = b, newC = c, newD = d, child;
			for(int i=0; i<I.length; i++)
			{
				int tA = (a + I[i]);
				newA = ((tA == a)? tA : ((tA) > 9)? 0 : ((tA) < 0)? 9 : tA);
				int tB = (b + II[i]);
				newB = ((tB == b)? tB : ((tB) > 9)? 0 : ((tB) < 0)? 9 : tB);
				int tC = (c + III[i]);
				newC = ((tC == c)? tC : ((tC) > 9)? 0 : ((tC) < 0)? 9 : tC);
				int tD = (d + IV[i]);
				newD = ((tD == d)? tD : ((tD) > 9)? 0 : ((tD) < 0)? 9 : tD);

				child =  (((((newA << 4) + newB) << 4) + newC) << 4) + newD;
				if(!done.get(child))
				{
					min[child] =  min[first] + 1;
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
