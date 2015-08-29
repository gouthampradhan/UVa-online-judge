package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class TheGreatEscape 
{
	
	/**
	 * Room class
	 * @author gouthamvidyapradhan
	 *
	 */
	static class Room
	{
		int index;
		int time;
		char data;
	}
	
	/**
	 * Room data
	 */
	private static List<Room> roomData;
	
	/**
	 * AdjList to store the graph
	 */
	private static List<List<Room>> graph;
	
	/**
	 * Mapping between room index and door index
	 */
	private static int[] door;
	
	/**
	 * Priority queue
	 */
	static java.util.Queue<Room> pq = new java.util.PriorityQueue<Room>(1000, new RoomComparator());
	
	/**
	 * Printwriter to write output
	 */
	static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	
	/**
	 * Array to hold the min distance
	 */
	static int[] minDistance;

	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Comparator
	 *
	 */
	static class RoomComparator implements Comparator<Room> 
	{

		@Override
		public int compare(Room o1, Room o2) 
		{
			return (o1.time < o2.time) ? -1 : (o1.time == o2.time) ? 0 :  1;
		}
	}

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
		 * Read string
		 * @return
		 * @throws Exception
		 */
		public char readChar() throws Exception
		{
			if(st != null && st.hasMoreTokens())
			{
				return st.nextToken().charAt(0);
			}
			String str = br.readLine();
			if(str != null && !str.equals(""))
			{
				st = new StringTokenizer(str);
				return st.nextToken().charAt(0);
			}				
			return ' ';
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
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		MyScanner scan = new MyScanner();
		byte tC = scan.readByte();
		for(byte i = 1; i<=tC; i++)
		{
			byte row = scan.readByte(); //number of rows 
			byte col = scan.readByte(); //number of cols
			int nR = row * col; // number of rooms
			roomData = new ArrayList<Room>(nR); // Create a room list of size nR
			roomData.add(0, null);
			graph = new ArrayList<List<Room>>(nR); // Create a AdjList of size nR
			for(int count=0; count<=nR; count++)
			{
				//Initialize graph
				graph.add(new ArrayList<Room>());
			}
			
			int roomIndex = 1;
			int doorIndex = 0;
			int start = nR - (col - 1);
			int finish = col;
			//Initialize door array size
			door = new int[500];
			for(byte r=0; r<row; r++)
			{
				String str = scan.readString();
				for(byte c=0; c<col; c++)
				{
					char current = str.charAt(c);
					//char current = scan.readChar();
					Room curRoom = new Room();
					curRoom.data = current;
					curRoom.index = roomIndex;
					curRoom.time = 1; // set 1 sec initially
					roomData.add(roomIndex, curRoom);
					if(current == '#')
					{
						roomIndex++; continue;
					}
					if(current == 'N' || current == 'S' || current == 'E' || current == 'W')
						door[doorIndex++] = roomIndex; //Map the door index to room index
					List<Room> childern;
					if((c-1) >= 0)
					//if((roomIndex - 1) > 0)
					{
						int lIndex = roomIndex - 1;
						Room lRoom = roomData.get(lIndex);
						switch(lRoom.data)
						{
							case '.':
								switch(curRoom.data)
								{
								case 'W':
									childern = graph.get(lIndex);
									childern.add(curRoom);//Add the right room the left room
									break;
								case '.':
									childern = graph.get(lIndex);
									childern.add(curRoom);//Add the right room the left room
								default:
									childern = graph.get(roomIndex);
									Room left = new Room();
									left.data = lRoom.data;
									left.index = lRoom.index;
									left.time = 1; // set 1 sec initially
									childern.add(left);//Add the left room to the right room
								}
								break;
								
							case 'E':
								if(curRoom.data != 'W')
								{
									childern = graph.get(roomIndex);
									Room left = new Room();
									left.data = lRoom.data;
									left.index = lRoom.index;
									left.time = 1; // set 1 sec initially
									childern.add(left);//Add the left room to the right room
								}
								break;
							case 'N':
							case 'S':
							case 'W':
								if(curRoom.data == 'W' || curRoom.data == '.')
								{
									childern = graph.get(lIndex);
									childern.add(curRoom);//Add the right room the left room
								}
								break;
						}
					}
					//if((roomIndex - col) > 0)
					if((r - 1) >= 0)
					{
						int uIndex = roomIndex - col;
						Room uRoom = roomData.get(uIndex);
						switch(uRoom.data)
						{
							case '.':
								switch(curRoom.data)
								{
								case 'N':
									childern = graph.get(uIndex);
									childern.add(curRoom);//Add the lower room the upper room
									break;
								case '.':
									childern = graph.get(uIndex);
									childern.add(curRoom);//Add the lower room the upper room
								default:
									childern = graph.get(roomIndex);
									Room up = new Room();
									up.data = uRoom.data;
									up.index = uRoom.index;
									up.time = 1; // set 1 sec initially
									childern.add(up);//Add the upper room the lower room
								}
								break;
								
							case 'S':
								if(curRoom.data != 'N')
								{
									childern = graph.get(roomIndex);
									Room up = new Room();
									up.data = uRoom.data;
									up.index = uRoom.index;
									up.time = 1; // set 1 sec initially
									childern.add(up);//Add the upper room the lower room
								}
								break;
							case 'N':
							case 'E':
							case 'W':
								if(curRoom.data == 'N' || curRoom.data == '.')
								{
									childern = graph.get(uIndex);
									childern.add(curRoom);//Add the lower room the upper room
								}
								break;
						}
					}
					roomIndex++;
				}
			}
			//End of construction of AdjList graph
			
			for(int d=0; d< doorIndex; d++)
			{
				int sec = scan.readInt();
				Room room = roomData.get(door[d]);
				int rIndex = room.index;
				int east = (rIndex - 1);
				int west = (rIndex + 1);
				int north = (rIndex - col);
				int south = (rIndex + col);
				List<Room> rList = graph.get(rIndex);
				switch(room.data)
				{
					case 'N':
						for(Room r : rList)
						{
							if(r.index == east || r.index == west)
							{
								r.time = r.time + sec;
							}
							else
							{
								r.time = r.time + (sec << 1);
							}
						}
						break;
					case 'S':
						for(Room r : rList)
						{
							if(r.index == east || r.index == west)
							{
								r.time = r.time + sec;
							}
							else
							{
								r.time = r.time + (sec << 1);
							}
						}
						break;
					case 'E':
						for(Room r : rList)
						{
							if(r.index == north || r.index == south)
							{
								r.time = r.time + sec;
							}
							else
							{
								r.time = r.time + (sec << 1);
							}
						}
						break;
					case 'W':
						for(Room r : rList)
						{
							if(r.index == north || r.index == south)
							{
								r.time = r.time + sec;
							}
							else
							{
								r.time = r.time + (sec << 1);
							}
						}
						break;
				}
			}
			
			int time = findPath(start, finish, graph, nR);
			if(time == Integer.MAX_VALUE)
				pw.println("Poor Kianoosh");
			else
				pw.println(time);
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
	static private int findPath(int from, int to, List<List<Room>> graph, int nR)
	{
		minDistance = new int[nR+1];
		for(int i=0; i<minDistance.length; i++)
		{
			minDistance[i] = Integer.MAX_VALUE;
		}
		Room start = new Room();
		start.index = from;
		start.time = 0;
		minDistance[start.index] = 0;
		pq.add(start);
		while(!pq.isEmpty())
		{
			Room parent = pq.remove();
			if(parent.time != minDistance[parent.index])
				continue;
			List<Room> e = graph.get(parent.index);
			for(Room child : e)
			{
				if((child.time + parent.time) < minDistance[child.index])
				{
					//Update the min distance
					minDistance[child.index] = child.time + parent.time;
					Room temp = new Room();
					temp.index = child.index;
					temp.time = child.time + parent.time;
					//temp.p = parent.i;
					pq.add(temp);
				}
			}
		}
		return minDistance[to];
	}

}
