package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class MonitoringTheAmazon {

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
			if (str != null) 
			{
				String s = str.trim();
				if (s.equals(""))
					return "";
				st = new StringTokenizer(s);
				return st.nextToken();
			}
			return null;
		}

		/**
		 * Read integer
		 * 
		 * @return
		 * @throws Exception
		 */
		public static int readInt() throws Exception {
			try {
				if (st != null && st.hasMoreTokens()) {
					return parseInt(st.nextToken());
				}
				String str = br.readLine();
				if (str != null && !str.trim().equals("")) {
					st = new StringTokenizer(str);
					return parseInt(st.nextToken());
				}
			} catch (IOException e) {
				close();
				return -1;
			}
			return -1;
		}
		
		/**
		 * Parse to integer
		 * @param in
		 * @return integer value
		 */
		private static int parseInt(String in)
		{
			// Check for a sign.
		    int num  = 0, sign = -1, i = 0;
		    final int len  = in.length( );
		    final char ch  = in.charAt( 0 );
		    if ( ch == '-' )
		        sign = 1;
		    else
		        num = '0' - ch;

		    // Build the number.
		    i+=1;
		    while ( i < len )
		        num = num*10 + '0' - in.charAt( i++ );
		    return sign * num;
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

	/**
	 * @author gouthamvidyapradhan
	 * Station class
	 *
	 */
	private static class Station
	{
		int x;
		int y;
		double d;
		int index;
	}
	
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Station comparator
	 *
	 */
	private static class StationComparator implements Comparator<Station>
	{

		@Override
		public int compare(Station s1, Station s2) 
		{
			if(s1.d == s2.d)
			{
				if(s1.x == s2.x)
					return (s1.y < s2.y)? -1 : ((s1.y > s2.y)? 1 : 0);
				else
					return (s1.x < s2.x)? -1 : ((s1.x > s2.x)? 1 : 0);
			}
			return (s1.d < s2.d)? -1 : ((s1.d > s2.d)? 1 : 0);
		}
	}
	
	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	
	private static Station[] stations;
	private static int N;
	private static List[] graph;
	private static int[] q;
	private static int count;
	private static final String REACHABLE = "All stations are reachable.";
	private static final String UNREACHABLE = "There are stations that are unreachable.";
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == 0) break;
			graph = new List[N];
			stations = new Station[N];
			q = new int[N];
			count = 0;
			//TODO handle this case if(N == 1 N == 2)
			for(int i=0; i<N; i++)
			{
				Station s = new Station();
				s.x = MyScanner.readInt();
				s.y = MyScanner.readInt();
				s.index = i;
				stations[i] = s;
			}
			Station mainStation =  stations[0];
			int x = mainStation.x;
			int y = mainStation.y;
			List<Station> list = new ArrayList<>();
			for(int j=1, l = stations.length; j<l; j++)
			{
				Station adj = stations[j];
				int cx = adj.x;
				int cy = adj.y;
				double dist = Math.sqrt(Math.pow(cx-x, 2) +  Math.pow(cy - y, 2));
				adj.d  = dist;
				list.add(adj);
			}
			Collections.sort(list, new StationComparator());
			graph[0] = new ArrayList<Station>();
			Station child1 = list.get(0);
			Station child2 = list.get(1);
			graph[0].add(child1);
			graph[0].add(child2);
			int c1 = child1.index;
			int c2 = child2.index;
			graph[c1] = new ArrayList<Station>();
			graph[c2] = new ArrayList<Station>();
			graph[c1].add(stations[0]); //make two way link
			graph[c2].add(stations[0]);
			q[0] = c1;
			q[1] = c2;
			count = 2;
			construct(0);
			if(count == N)
				pw.println(REACHABLE);
			else
				pw.println(UNREACHABLE);
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Construct the graph using BFS
	 * @param parent
	 * @param start
	 */
	private static void construct(int parent)
	{
		int head = 0, tail = 2;
		while(head < tail)
		{
			int first = q[head++];
			List<Station> children = graph[first];
			if(children == null)
				children = new ArrayList<>();
			graph[first] = children;
			if(children.size() < 2)
			{
				Station s = stations[first];
				int x = s.x; int y = s.y;
				List<Station> list = new ArrayList<>();
				for(int i=0, l = stations.length; i<l; i++)
				{
					if(i != parent && i != first)
					{
						Station adj = stations[i];
						int cx = adj.x;
						int cy = adj.y;
						double dist = Math.sqrt(Math.pow(cx - x, 2) +  Math.pow(cy - y, 2));
						adj.d = dist;
						list.add(adj);
					}
				}
				Collections.sort(list, new StationComparator());
				Station child = list.get(0);
				children.add(child);
				List<Station> reverseLink = graph[child.index];
				if(reverseLink == null)
					reverseLink = new ArrayList<>();
				graph[child.index] = reverseLink;
				reverseLink.add(s); //make two way link
				q[tail++] = child.index;
				count++;
			}
		}
	}
}
