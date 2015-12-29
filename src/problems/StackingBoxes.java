package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.112 s. Tricky problem involving multidimensional boxes, requires sorting and then longest path algorithm (toposort and relax vertices where 
 * length from one node to another is a constant 1)
 *
 */
public class StackingBoxes {

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
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
        	return br.ready();
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
    
    private static List<List<Integer>> graph;
    private static int K, N, top, max, root;
    private static int[] toposort, maxArr, parent; //stack of toposorted vertices
    private static BitSet done = new BitSet();
    private static List<Box> stackOfBoxes;
    private static final String BLANK = " ";
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Box class
     * @author gouthamvidyapradhan
     *
     */
    private static class Box
    {
    	int id;
    	int[] dimensions;
    	Box(int id)
    	{
    		this.id = id;
    	}
    }
    
    /**
     * Box comparator
     * @author gouthamvidyapradhan
     *
     */
    private static class BoxComparator implements Comparator<Box>
    {

		@Override
		public int compare(Box b1, Box b2) 
		{
			int[] d1 = b1.dimensions;
			int[] d2 = b2.dimensions;
			for(int i = 0, l = d1.length; i < l; i++)
			{
				int a = d1[i]; int b = d2[i];
				if(a < b) return -1;
				else if(a > b) return 1;
			}
			return 0;
		}
    	
    }
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
    	while(true)
    	{
    		String line = MyScanner.readLine();
    		if(line == null) break;
    		if(line.trim().equals("")) continue;
    		StringTokenizer st = new StringTokenizer(line.trim());
    		K = MyScanner.parseInt(st.nextToken()); N = MyScanner.parseInt(st.nextToken());
    		toposort = new int[K]; top = -1; max = 0;
    		maxArr = new int[K]; parent = new int[K];
    		stackOfBoxes = new ArrayList<Box>(K);
    		graph = new ArrayList<>();
    		for(int i = 0; i<K; i++)
    		{
    			graph.add(i, new ArrayList<Integer>());
    			maxArr[i] = 1; parent[i] = -1;
    		}
    		for(int i = 0; i < K; i++)
    		{
    			int[] dimensions = new int[N];
    			for(int j = 0; j<N; j++)
    				dimensions[j] = MyScanner.readInt();
    			Arrays.sort(dimensions);
    			Box box = new Box(i);
    			box.dimensions = dimensions;
    			stackOfBoxes.add(box);
    		}
    		Collections.sort(stackOfBoxes, new BoxComparator());
			for(int i = 0, l = stackOfBoxes.size(); i < l; i++)
			{
				for(int j = i + 1; j < l; j++)
				{
					Box b1 = stackOfBoxes.get(i);
					Box b2 = stackOfBoxes.get(j);
					int[] d1 = b1.dimensions;
					int[] d2 = b2.dimensions;
					if(isNestable(d1, d2)) graph.get(b2.id).add(b1.id);
				}
			}
			for(int i = 0; i<K; i++) //toposort
    		{
    			if(!done.get(i))
    				dfs(i);
    		}
    		for(int i = top; i >= 0; i--) //relax each of the toposorted vertices
    			relax(toposort[i]);
    		pw.println(max); //print length
    		print(root); //print stack of boxes
    		pw.println(); //new line
    		done.clear(); graph.clear();
    	}
    	pw.flush(); pw.close(); MyScanner.close();
    }
    
    /**
     * Print stack of boxes
     * @param root
     */
    private static void print(int root)
    {
    	pw.print(root + 1);
    	if(parent[root] != -1)
    	{
    		pw.print(BLANK);
    		print(parent[root]);
    	}
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
    	List<Integer> children = graph.get(i);
    	for(int c : children)
    	{
    		if((maxArr[i] + 1) > maxArr[c])
    		{
    			maxArr[c] = (maxArr[i] + 1);
    			parent[c] = i;
    		}
    	}
    }
    
    /**
     * Check if the boxes are nestable
     * @param box1
     * @param box2
     * @return
     */
    private static boolean isNestable(int[] box1, int[] box2)
    {
    	for(int i=0, l = box1.length; i < l; i++)
    	{
    		if(box1[i] < box2[i]) continue;
    		return false;
    	}
    	return true;
    }
    
    /**
     * Dfs to toposort vertices
     * @param i
     */
    private static void dfs(int i)
    {
    	done.set(i);
    	List<Integer> children = graph.get(i);
    	for(int c : children)
    	{
    		if(!done.get(c))
    			dfs(c);
    	}
    	toposort[++top] = i; //push this to top of stack
    }
}
