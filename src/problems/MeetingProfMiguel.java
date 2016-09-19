package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.055 s. Simple Floyd-Warshall's algorithm. Tricky test cases.
 *
 */
public class MeetingProfMiguel {

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
                    return parseInt(st.nextToken());
                }
                String str = br.readLine();
                if(str == null) return -2;
                if (!str.trim().equals("")) {
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
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
            return br.ready();
        }

        /**
         * Read line
         * @return
         * @throws Exception
         */
        public static String readLine() throws Exception
        {
            return br.readLine();
        }
        /**
         * Parse to integer
         * @param in
         * @return integer value
         */
        public static int parseInt(String in)
        {
            // Check for a sign.
            int num  = 0, sign = -1, i = 0;
            final int len  = in.length( );
            final char ch  = in.charAt( 0 );
            if ( ch == '-' )
                sign = 1;
            else
                num = '0' - ch;

            // Build the number
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int[][] M, Y;
    private static int N, u, v, w, MIN, mPos, yPos;
    private static Set<Integer> nodeSet = new HashSet<>(); //maintain a set of unique nodes
    private static Map<Integer, List<Integer>> meetingPoint = new HashMap<>();
    private static int[] nodes;
    private static final String NEVER_MEET = "You will never meet.", BLANK = " ";
    private static final String CITY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == 0) break;
			M = new int[28][28]; Y = new int[28][28]; //can't exceed 26 characters 
			MIN = Integer.MAX_VALUE;
            for(int i = 0; i <= 26; i++) //initialize with -1, because there could be paths with energy 0.
                for(int j = 0; j <= 26; j++)
                {
                	M[i][j] = -1;
                	Y[i][j] = -1;
                }
			for(int i = 0; i < N; i++)
			{
				String line = MyScanner.readLine();
				StringTokenizer st = new StringTokenizer(line);
				while(st.hasMoreTokens())
					addEdge(st.nextToken().equals("M") ? M : Y, st.nextToken(), st.nextToken(), st.nextToken(), MyScanner.parseInt(st.nextToken()));
			}
			String line = MyScanner.readLine();
			StringTokenizer st = new StringTokenizer(line);
			yPos = CITY.indexOf(st.nextToken()); //initial position of professor and the youth.
			mPos = CITY.indexOf(st.nextToken());
            Object[] temp = nodeSet.toArray();
            N = temp.length; //reuse N
            nodes = new int[N];
            for(int i = 0; i < N; i++)
            	nodes[i] = (int)temp[i]; //copy to simple array
            apsp(); //invoke all pair shortest path
            for(int i = 0; i < N; i++)
            {
            	if(M[mPos][nodes[i]] == -1 || Y[yPos][nodes[i]] == -1) continue;
            	int newMin = M[mPos][nodes[i]] + Y[yPos][nodes[i]];
            	MIN = Math.min(MIN, newMin); //maintain a new Min
            	saveNewMin(newMin, nodes[i]);
            }
            if(M[mPos][yPos] != -1) //check if there is a minimum path from Professors initial position to Youth' initial position
            {
            	MIN = Math.min(MIN, M[mPos][yPos]);
            	saveNewMin(M[mPos][yPos], yPos);	
            }
            if(Y[yPos][mPos] != -1) //check if there is a minimum path from Youth's initial position to Professor's initial position
            {
            	MIN = Math.min(MIN, Y[yPos][mPos]);
            	saveNewMin(Y[yPos][mPos], mPos);	
            }
            if(yPos == mPos) //if both are at the same position
            {
            	MIN = 0;
            	saveNewMin(0, mPos);	
            }
            if(MIN == Integer.MAX_VALUE)
            	pw.println(NEVER_MEET);
            else
            {
            	List<Integer> cities = meetingPoint.get(MIN);
            	Collections.sort(cities); //sort to print lexicographical order
            	pw.print(MIN);
            	for(int c : cities)
            		pw.print(BLANK + CITY.charAt(c));
            	pw.println();
            }
            nodeSet.clear(); meetingPoint.clear();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Save a new minimum
	 * @param newMin
	 * @param vertex
	 */
	private static void saveNewMin(int newMin, int vertex)
	{
    	if(meetingPoint.get(newMin) == null)
    		meetingPoint.put(newMin, new ArrayList<Integer>());
    	meetingPoint.get(newMin).add(vertex); //add the possible meeting point to list
	}
	/**
	 * Method to add a new edge
	 * @param graph
	 * @param direction
	 * @param from
	 * @param to
	 * @param distance
	 */
	private static void addEdge(int[][] graph, String direction, String from, String to, int distance)
	{
		int u = CITY.indexOf(from);
		int v = CITY.indexOf(to);
		if(graph[u][v] != -1)
			graph[u][v] = Math.min(graph[u][v], distance);
		else graph[u][v] = distance;
		if(direction.equals("B"))
		{
			if(graph[v][u] != -1)
				graph[v][u] = Math.min(graph[v][u], distance);
			else graph[v][u] = distance; //make two way connection
		}
		nodeSet.add(u);
		nodeSet.add(v);
	}
	
    /**
     * Floyd-Warshall's all pair shortest path algorithm
     */
    private static void apsp()
    {
        for(int i = 0; i < N; i++)
        {  
        	w = nodes[i];
            for(int j = 0; j < N; j++) 
            {
            	u = nodes[j];
                for (int k = 0; k < N; k++)
                {
                	v = nodes[k];
                	if(u == v) continue; 
                	//calculate the shortest path for two different graphs
            		if((M[u][w] != -1) && M[w][v] != -1)
            		{
            			if(M[u][v] == -1) M[u][v] = M[u][w] + M[w][v]; //alternate route is the best route.
            			else M[u][v] = Math.min(M[u][v], M[u][w] + M[w][v]);
            		}
            		if((Y[u][w] != -1) && Y[w][v] != -1)
            		{
            			if(Y[u][v] == -1) Y[u][v] = Y[u][w] + Y[w][v]; //alternate route is the best route.
            			else Y[u][v] = Math.min(Y[u][v], Y[u][w] + Y[w][v]);
            		}
                }
            }
        }
    }

}
