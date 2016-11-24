package problems.uva.moreadvancedtopics;

import java.io.*;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by PRADHANG on 11/24/2016.
 * Accepted 0.300
 * The unique node index is represented as below
 * ROW            COLUMN         DIRECTION (N S E W)      NUMBER_OF_ROTATION (0, 1, 2 or 3)
 * First 9 bits   Next 9 bits    Next 4 bits              Last 3 bits
 */
public class RoundAndRoundMaze
{
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
    private static final int CELL_CONST = 511;
    private static final int STATE_CONST = 15;
    private static final int ROTATE_CONST = 7;
    private static final int[] R = {0, 1, 0, -1}; //W S E N
    private static final int[] C = {-1, 0, 1, 0};
    private static final String NO_PATH = "no path to exit";
    private static int NR, NC;
    private static int[][] maze;
    private static int[][] min;
    private static BitSet done = new BitSet();
    private static Queue<Integer> queue = new ArrayDeque<>();

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            while((NR = MyScanner.readInt()) == -1);
            if(NR == -2) break;
            NC = MyScanner.readInt();
            maze = new int[NR][NC];
            min = new int[NR][NC];
            String line;
            done.clear();
            queue.clear();
            for(int i = 0; i < NR; i ++)
            {
                for(int j = 0; (i == NR - 1) ? j < NC - 1 : j < NC; j ++)
                {
                    line = MyScanner.readLine();
                    int state = 0;
                    for(int k = 0, l = line.length(); k < l; k++)
                    {
                        switch (line.charAt(k))
                        {
                            case 'N':
                                state |= 1 << 3;
                                break;
                            case 'S':
                                state |= 1 << 1;
                                break;
                            case 'E':
                                state |= 1 << 2;
                                break;
                            case 'W':
                                state |= 1;
                                break;
                        }
                    }
                    maze[i][j] = (((i << 9) | (j)) << 4) | (state);
                }
            }
            bfs(maze[0][0]);
            if(min[NR - 1][NC - 1] == 0)
                pw.println(NO_PATH);
            else
                pw.println(min[NR - 1][NC - 1]);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Bfs to find the shortest path
     * @param cell cell
     * @return shortest path integer
     */
    private static void bfs(int cell)
    {
        done.set(cell << 3);
        queue.offer(cell << 3); //enqueue
        while(!queue.isEmpty())
        {
            int head = queue.remove();
            int rotations = head & ROTATE_CONST; //current rotations
            int direction = (head >> 3) & STATE_CONST; //direction state
            int c = (head >> 7) & CELL_CONST;
            int r = (head >> 16) & CELL_CONST;
            int newR, newC, currState, newState, newRotation, currRotation, temp;
            for(int i = 0; i < 4; i ++)
            {
                if((direction & (1 << i)) > 0)
                {
                    newR = r + R[i];
                    newC = c + C[i];
                    if(newR < 0 || newR >= NR || newC < 0 || newC >= NC) continue;
                    currState = maze[newR][newC];
                    currRotation = currState & 15;
                    newRotation = ((currRotation >>> ((rotations + 1) % 4)) | (currRotation << (4 - ((rotations + 1) % 4)))) & 15;
                    temp = currState >> 4; //clear last 4 bits
                    temp = temp << 4;
                    newState  = temp | newRotation;
                    newState = (newState << 3) | ((rotations + 1) % 4); //include the new rotation count
                    if(!done.get(newState))
                    {
                        done.set(newState);
                        queue.add(newState);
                        min[newR][newC] = min[r][c] + 1;
                        if(newR == NR - 1 && newC == NC - 1) //destination reached break
                        {
                            queue.clear();
                            break;
                        }
                    }
                }
            }
        }
    }
}