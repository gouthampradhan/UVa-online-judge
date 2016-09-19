package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.385. Really struggled to understand how to model the graph (during my vacation in Bangalore at my home). Once the graph was modelled correctly
 * the solution was quite straight forward. 
 * @see StackingBoxes
 *
 */
public class TowerOfCubes {

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
    
    private static Cube[] graph;
    private static int N, max, root, count, index;
    private static int[] maxArr, parent; //max length of each node
    private static Map<Integer, List<Integer>> bottomIndex;
    private static final String BLANK = " ";
    private static final String CASE = "Case #";
    private static final String[] cubeFace = {"front", "back", "left", "right", "top", "bottom"};
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Cube class
     * @author gouthamvidyapradhan
     *
     */
    private static class Cube
    {
    	int id; int b; int t; int pos; int facePos;
    	Cube(int id, int t, int b, int pos, int facePos)
    	{
    		this.id = id;
    		this.b = b;
    		this.t = t;
    		this.pos = pos;
    		this.facePos = facePos;
    	}
    	List<Integer> children = new ArrayList<>();
    }
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
    	count = 0;
    	while(true)
    	{
    		while((N = MyScanner.readInt()) == -1);
    		if(N == 0) break;
    		index = 0; max = 0; 
    		maxArr = new int[3001]; parent = new int[3001]; graph = new Cube[3001];
    		bottomIndex = new HashMap<>();
    		List<Cube> temp = new ArrayList<>();
    		for(int i = 0; i<3001; i++)
    		{
    			parent[i] = -1; maxArr[i] = 1;
    		}
    		for(int i=0; i<N; i++)
    		{
    			int facePos = 0; //initialize facePos to 0
        		for(int j=0; j<3; j++)
        		{
        			int face1 = MyScanner.readInt(); //cube face1
        			int face2 = MyScanner.readInt(); //cube face2
        			Cube c1 = new Cube(index++, face1, face2, i, facePos++);
        			Cube c2 = new Cube(index++, face2, face1, i, facePos++);
        			temp.add(c1); temp.add(c2);
        			addChildren(c1); addChildren(c2); //include children
        			graph[c1.id] = c1; graph[c2.id] = c2; //add this to graph
        		}
        		for(Cube c : temp)
        		{
        			List<Integer> list = bottomIndex.get(c.b);
        			if(list == null) list = new ArrayList<>(); 
        			list.add(c.id); //include the current cube in the bottom index
        			bottomIndex.put(c.b, list);
        		}
        		temp.clear(); //clear temp list
    		}
    		for(int i = index-1; i >= 0; i--)
    			relax(i);
    		if(count > 0) pw.println();
    		pw.println(CASE + ++count);
    		pw.println(max);
    		print(root);
    	}
    	pw.flush(); pw.close(); MyScanner.close();
    }
    
    /**
     * Add children to nodes
     * @param c
     */
    private static void addChildren(Cube c)
    {
		List<Integer> bottomList = bottomIndex.get(c.t);
		if(bottomList != null) c.children.addAll(bottomList);
    }
    /**
     * Print stack of boxes
     * @param root
     */
    private static void print(int root)
    {
    	Cube cube = graph[root];
    	pw.println(cube.pos+1 + BLANK + cubeFace[cube.facePos]);
    	if(parent[root] != -1)
    		print(parent[root]);
    }
    
    /**
     * Relax each vertices
     * @param i
     */
    private static void relax(int i)
    {
    	if(maxArr[i] > max) 
    	{
    		max = maxArr[i];
    		root = i;
    	}
    	List<Integer> children = graph[i].children;
    	for(int c : children)
    	{
    		if((maxArr[i] + 1) > maxArr[c])
    		{
    			maxArr[c] = (maxArr[i] + 1);
    			parent[c] = i;
    		}
    	}
    }
}
