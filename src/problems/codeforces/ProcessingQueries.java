package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted CodeForces 202ms.
 *
 */
public class ProcessingQueries 
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 500000)); //set the buffer too high.
    private static int N, b;
    private static long serverFreeAt; //time when the server is next available.
    private static long[] done;
    private static Job[] todo; 
    private static Queue<Job> queue = new ArrayDeque<Job>(200002);
    private static final String BLANK = " ";


    /**
     * Job class
     * @author gouthamvidyapradhan
     *
     */
    private static class Job
    {
        int i, t, d;
        Job(int i, int t, int d)
        {
            this.i = i;
            this.t = t;
            this.d = d;
        }
    }


    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        N = MyScanner.readInt();
        b = MyScanner.readInt();
        todo = new Job[N];
        done = new long[N + 1];
        for(int i = 0; i < N; i++)
            todo[i] = new Job(i + 1, MyScanner.readInt(), MyScanner.readInt());

        serverFreeAt = 0;
        for(Job j : todo)
            consume(j);
        while(!queue.isEmpty())
        {
        	Job j = queue.remove();
            serverFreeAt += j.d;
            done[j.i] = serverFreeAt;
        }
        for(int i = 1; i <= N; i++)
        {
            if(i > 1)
                pw.print(BLANK);
            pw.print(done[i]);
        }
        pw.println();
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * Consume jobs
     */
    private static void consume(Job job)
    {
        if(serverFreeAt <= job.t)
        {
        	if(queue.isEmpty())
            {
            	serverFreeAt = job.t + job.d; 
                done[job.i] = serverFreeAt;
            }
            else
            {
            	while(!queue.isEmpty())
                {
            		Job j = queue.remove();
                    serverFreeAt += j.d;
                    done[j.i] = serverFreeAt;
                    if(serverFreeAt > job.t)
                    {
                    	queue.add(job);
                        break;
                    }
                }
            	if(queue.isEmpty())
                {
                	serverFreeAt = job.t + job.d; 
                    done[job.i] = serverFreeAt;
                }
            }
        }
        else
        {
        	if(queue.size() < b)
            	queue.add(job);
            else done[job.i] = -1; //queue full
        }
    }
}