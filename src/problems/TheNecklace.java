package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 1.008 s. 
 * My Algorithm : 2 x O(N) 
 * 1. Check if each vertex has even degree
 * 2. Check if the graph is connected
 * 3. Run the algorithm for euler tour 
 * 	  i.Start from any vertex and check each edge of visited status, if unvisited then, mark the edge as visited and add it to the list called cycle.
 *   ii. Mark it's back-edge of the previous edge as also visited.
 *  iii. Pick the adjacent vertex from the previous edge and continue from step i recursively.
 *
 */
public class TheNecklace {

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
    private static int N, T;
    private static List<List<Vertex>> graph;
    private static final String BLANK = " ";
    private static BitSet isOrderOdd = new BitSet();
    private static final String LOST_BEADS = "some beads may be lost", CASE = "Case #";
    
    /**
     * Class to store vertex
     * @author gouthamvidyapradhan
     *
     */
    private static class Vertex
    {
        int v;
        boolean done;
        Vertex(int v, boolean done)
        {
            this.v = v;
            this.done = done;
        }
    }


    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        T = MyScanner.readInt();
        int count = 0;
        while(T-- > 0)
        {
            N = MyScanner.readInt();
            graph = new ArrayList<>();
            for(int i = 0; i <= 1002; i++)
                graph.add(i, new ArrayList<Vertex>());
            int from = 0;
            for(int i = 0; i < N; i++)
            {
                from = MyScanner.readInt();
                int to = MyScanner.readInt();
                graph.get(from).add(new Vertex(to, false));
                graph.get(to).add(new Vertex(from, false)); //make two way connection

                if((graph.get(from).size() % 2) == 1)
                    isOrderOdd.set(from);
                else isOrderOdd.clear(from);

                if((graph.get(to).size() % 2) == 1)
                    isOrderOdd.set(to);
                else isOrderOdd.clear(to);
            }
            if(count != 0)
                pw.println();
            pw.println(CASE + ++count);
            if(!isOrderOdd.isEmpty()) //check if each vertices has even order
                pw.println(LOST_BEADS);
            else
            {
                List<Integer> cycle = new ArrayList<>();
                cycle.add(from);
                eulerTour(from, 1, cycle);
                if(cycle.size() != N + 1) //check if the graph is connected
                    pw.println(LOST_BEADS);
                else
                    for(int i=0, j=1, l = cycle.size(); j < l; i++, j++)
                        pw.println(cycle.get(i) + BLANK + cycle.get(j));
            }
            isOrderOdd.clear();
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Construct the euler tour path and save the path in a list called cycle.
     * @param v
     */
    private static void eulerTour(int v, int pos, List<Integer> cycle)
    {
        List<Vertex> children = graph.get(v);
        for (Vertex c : children)
        {
            if(c.done) continue;
            c.done = true;
            List<Vertex> grandChildren = graph.get(c.v);
            for (Vertex gc : grandChildren) { //check for back-edge and mark this as visited.
                if (gc.v == v && !gc.done) {
                    gc.done = true;
                    break;
                }
            }
            cycle.add(pos, c.v); //adding this in the appropriate position is important
            eulerTour(c.v, pos + 1, cycle);
       }
    }
}