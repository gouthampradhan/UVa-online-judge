package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 389 ms. Struggled a lot with understanding the problem and coding. Have to improve a 1000 mile in all aspects.
 *
 */
public class GraphColoring {

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
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static long readLong() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return Long.parseLong(st.nextToken());
                }
                String str = br.readLine();
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return Long.parseLong(st.nextToken());
                }
            } catch (IOException e) {
                close();
                return -1;
            }
            return -1;
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
    private static int N, M;
    private static List<List<Node>> graph;
    private static List<Integer> red = new ArrayList<>(),
    		blue = new ArrayList<>();
    private static int[] visited;
    private static List<Integer>[] group = new List[2];
    /**
     * 
     * @author gouthamvidyapradhan
     *
     */
    private static class Node
    {
    	int v;
    	String color;
    	Node(int v, String color)
    	{
    		this.v = v;
    		this.color = color;
    	}
    }
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		N = MyScanner.readInt();
		M = MyScanner.readInt();
		graph = new ArrayList<>(N + 1);
		visited = new int[N + 1];
		group[0] = new ArrayList<>();
		group[1] = new ArrayList<>();
		for(int i = 0; i <= N; i++)
			graph.add(new ArrayList<Node>());
		for(int i = 1; i <= M; i++)
		{
			String line = MyScanner.readLine();
			StringTokenizer st = new StringTokenizer(line);
			int from = MyScanner.parseInt(st.nextToken());
			int to = MyScanner.parseInt(st.nextToken());
			String color = st.nextToken().trim();
			graph.get(from).add(new Node(to, color));
			graph.get(to).add(new Node(from, color));
		}
		boolean redPossible = true;
		boolean bluePossible = true;
		redPossible = run("R", red);
		bluePossible = run("B", blue);
		if(!redPossible && !bluePossible)
			pw.println(-1);
		else if(redPossible && bluePossible)
		{
			if(red.size() <= blue.size())
				print(red);
			else 
				print(blue);
		}
		else if(redPossible)
			print(red);
		else
			print(blue);
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * 
	 * @param color
	 * @param result
	 * @return
	 */
	private static boolean run(String color, List<Integer> result)
	{
		Arrays.fill(visited, -1);
		for(int i = 1; i <= N; i++)
		{
			if(visited[i] < 0)
			{
				group[0].clear(); group[1].clear();
				if(!dfs(i, 0, color))
					return false;
				if(group[0].size() <= group[1].size())
					result.addAll(group[0]);
				else
					result.addAll(group[1]);
			}
		}
		return true;
	}
	/**
	 * Print result
	 * @param result
	 */
	private static void print(List<Integer> result)
	{
		pw.println(result.size());
		Iterator<Integer> ite = result.iterator();
		int count = 0;
		while(ite.hasNext())
		{
			if(count++ != 0)
				pw.print(" ");
			pw.print(ite.next());
		}
		pw.println();
	}
	/**
	 * 
	 * @param v
	 * @param color
	 * @return
	 */
	private static boolean dfs(int v, int p, String color)
	{
		if(visited[v] > -1) return visited[v] == p; 
		visited[v] = p;
		group[p].add(v);
		List<Node> children = graph.get(v);
		for(Node child : children)
		{
			if(!dfs(child.v, child.color.equals(color) ? p : p ^ 1, color)) //very important my all other attempts using HashSet failed.
				return false;
		}
		return true;
	}
}