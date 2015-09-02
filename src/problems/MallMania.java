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
		List<Vertex> childern;
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
	
	public static void main(String[] args) throws Exception
	{
		MyScanner scan = new MyScanner();
		while(true)
		{
			int p = scan.readInt();
			if(p == 0)
				break;
			for(int i=0; i<p; i++)
			{
				int x = scan.readInt();
				int y = scan.readInt();
				xMin = (x < xMin) ? x : xMin;
				xMax = (x > xMax) ? x : xMax;
				yMin = (y < yMin) ? y : yMin;
				yMax = (y > yMax) ? y : yMax;
				Mall temp = new Mall();
				temp.perimeter = p;
				List<String> co;
				if(temp.coordinates == null)
					co = new ArrayList<>();
				else
					co = temp.coordinates;
				String key = x+"x"+y;
				co.add(key);
				vertices.put(key, 0);//set the value to 0 initially
				temp.coordinates = co;
				//There are only two malls possible
				if(m1 == null)
					m1 = temp;
				else
					m2 = temp;
			}
			//Build graph
			int vCnt = 0;
			int col = (yMax - yMin);
			nV = col * (xMax - xMin);
			q = new int[nV];
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
			
			int start;
			
			if(m1.perimeter <= m2.perimeter)
				start = vertices.get(m1.coordinates.get(0));
			else
				start = vertices.get(m2.coordinates.get(0));
			
			pw.println(bfs(start));
		}
	}
	
	/**
	 * Bfs to find the shortest distance
	 * @param s
	 * @return
	 */
	private static int bfs(int s)
	{
		Vertex start = graph.get(s);
		start.distance = 0;
		int head=0;
		int tail=1;
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
						//if(c.index == 0) Check for the found condition
						//return c.distance
						c.distance = v.distance + 1;
						q[tail++] = c.index;
					}
				}
				v.done = true;
			}
		}
		return -1; //-1 indicating no distance.
	}
}
