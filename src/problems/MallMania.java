package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		List<String> coordinates;
	}
	
	/**
	 * Vertex node to hold the graph
	 * @author gouthamvidyapradhan
	 *
	 */
	static class Vertex
	{
		int index;
		int distance;
		List<Vertex> childern = new ArrayList<>();
		boolean done;
	}
	
	/**
	 * Mapping between coordinates and vertex
	 */
	static Map<String, Integer> vertices = new HashMap<>();
	
	static List<Vertex> graph = new ArrayList<>();
	
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
			
			//Build graph
			int vCnt = 0;
			int col = ((xMax+1) - xMin);
			nV = col * ((yMax+1) - yMin);
			graph.add(new Vertex());//set 0 pos to empty Vertex
			for(int y=yMax; y>=yMin; y--)
			{
				for(int x=xMin; x<=xMax; x++)
				{
					Vertex v = new Vertex();
					v.index = ++vCnt;
					graph.add(vCnt, v);
					if((x - 1) >= xMin) //make two way connection to left node
					{
						Vertex left = graph.get(x-1);
						left.childern.add(v);
						v.childern.add(left);
					}
					if((y + col) <= yMax) //make two way connection to top node
					{
						Vertex top = graph.get(y + col);
						top.childern.add(v);
						v.childern.add(top);
					}
					if(vertices.get(x+"x"+y) != null)
						vertices.put(x+"x"+y, vCnt); //store the vertex of the mall
				}
			}
			
			List<Integer> start = new ArrayList<>();
			List<Integer> destination = new ArrayList<>();
			int minDistance = Integer.MAX_VALUE;
			if(m1.perimeter <= m2.perimeter)
			{
				for(int v = 0; v<m1.coordinates.size(); v++)
					start.add(vertices.get(m1.coordinates.get(v)));

				for(int v = 0; v<m2.coordinates.size(); v++)
					destination.add(vertices.get(m2.coordinates.get(v)));
			}
			else
			{
				for(int v = 0; v<m2.coordinates.size(); v++)
					start.add(vertices.get(m2.coordinates.get(v)));
				
				for(int v = 0; v<m1.coordinates.size(); v++)
					destination.add(vertices.get(m1.coordinates.get(v)));
			}
			for(int s : start)
				minDistance = Math.min(minDistance, bfs(s, destination));
			pw.println(minDistance);
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
		for(int i=0; i<p; i++)
		{
			int x = scan.readInt();
			int y = scan.readInt();
			xMin = (x < xMin) ? x : xMin;
			xMax = (x > xMax) ? x : xMax;
			yMin = (y < yMin) ? y : yMin;
			yMax = (y > yMax) ? y : yMax;
			List<String> co;
			m.perimeter = p;
			if(m.coordinates == null)
				m.coordinates = co = new ArrayList<>();
			else
				co = m.coordinates;
			String key = x+"x"+y;
			co.add(key);
			vertices.put(key, 0);//set the value to 0 initially
		}
	}
	
	static private void reset()
	{
		m1 = null;
		m2 = null;
		graph.clear();
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
	private static int bfs(int s, List<Integer> destination)
	{
		Vertex start = graph.get(s);
		start.distance = 0;
		if(destination.contains(start.index))
			return 0; // if mall 1 share the same coordinates as mall 2
		int head=0;
		int tail=1;
		q = new int[nV];
		q[tail++] = s;
		while(head < tail)
		{
			int first = q[head++];
			Vertex v = graph.get(first);
			if(!v.done)
			{
				List<Vertex> childern = v.childern;
				for(Vertex c : childern)
				{
					if(!c.done)
					{
						c.distance = v.distance + 1;
						if(destination.contains(c.index)) //Reached one of the coordinates of m2
							return c.distance;
						q[tail++] = c.index;
					}
				}
				v.done = true;
			}
		}
		return Integer.MAX_VALUE; //Integer.MAX_VALUE indicating no distance.
	}
}
