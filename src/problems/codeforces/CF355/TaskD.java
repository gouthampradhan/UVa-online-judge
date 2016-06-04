package problems.codeforces.CF355;

import problems.codeforces.CF353.MyScanner;
import java.io.PrintWriter;
import java.util.*;

/**
 * Accepted 1.216 s. Very challenging SSSP, hard to run this within Time Limit.
 *
 */
public class TaskD
{
    static class Cell
    {
        int r, c, distance;
        Cell(int r, int c)
        {
            this.r = r;
            this.c = c;
        }
    }

    private static final int[] I = {0, 1, 0, -1, 1, 0, -1, 0};
    private static BitSet done = new BitSet(154000);
    private static int[][] DP;
    private static int[][] palace;
    private static int[][] tempDP;
    private static Map<Integer, List<Cell>> color;
    private static List<Cell> sorted;
    private static int[] q;
    private static int N, M, P, h, t, currIndex;
    private static final int CONST = 511;

    /**
     *
     * @param testNumber
     * @param in
     * @param out
     */
    public void solve(int testNumber, MyScanner in, PrintWriter out)
    {
        N = in.nextInt();
        M = in.nextInt();
        P = in.nextInt();
        DP = new int[N][M];
        tempDP = new int[N][M];
        palace = new int[N][M];
        color = new HashMap<>();
        sorted = new ArrayList<>();
        int pr = 0, pc = 0;
        for(int i = 0; i < N; i ++ )
            Arrays.fill(DP[i], Integer.MAX_VALUE);

        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++)
            {
                palace[i][j] = in.nextInt();
                List<Cell> list = color.get(palace[i][j]);
                if(list == null)
                    list = new ArrayList<>();
                list.add(new Cell(i, j));
                color.put(palace[i][j], list);
                if(palace[i][j] == 1)
                    DP[i][j] = i + j;
                if(palace[i][j] == P)
                {
                    pr = i; pc = j; //store the dest row, col
                }
            }

        q = new int[N * M];
        for(int i = 1; i < P; i++)
        {
            List<Cell> source = color.get(i);
            List<Cell> dest = color.get(i + 1);
            int sourceCnt = source.size();
            int destCnt = dest.size();
            if(sourceCnt * destCnt <= N * M * 10)
            {
                for(Cell s : source)
                    for(Cell d : dest)
                        DP[d.r][d.c] = Math.min(DP[d.r][d.c], DP[s.r][s.c] + Math.abs(d.r - s.r) + Math.abs(d.c - s.c));
            }
            else
            {
                h = 0; t = 0;
                done.clear();
                sorted.clear();
                for (Cell s : source)
                {
                    s.distance = DP[s.r][s.c];
                    sorted.add(s);
                }
                Collections.sort(sorted, (o1, o2) -> Integer.compare(o1.distance, o2.distance));
                Cell first = sorted.get(0);
                q[t++] = (first.r << 9) + first.c;
                tempDP[first.r][first.c] = first.distance;
                done.set((first.r << 9) + first.c);
                currIndex = 1;
                bfs(i + 1, sourceCnt, destCnt);
            }
        }
        out.println(DP[pr][pc]);
    }

    /**
     * Bfs to find the shortest path
     * @param dest destination color
     */
    private static void bfs(int dest, int sourceCnt, int destCnt)
    {
        int dCnt = 0;
        while(h < t && dCnt < destCnt)
        {
            int cell = q[h++];
            int r = cell >> 9;
            int c = cell & CONST;

            while((currIndex < sourceCnt)
                    && (sorted.get(currIndex).distance == tempDP[r][c]))

            {
                Cell curr = sorted.get(currIndex++);
                int rowCol = (curr.r << 9) + curr.c;
                if(!done.get(rowCol))
                {
                    q[t++] = rowCol;
                    done.set(rowCol);
                    tempDP[curr.r][curr.c] = curr.distance;
                }
            }

            for(int i = 0; i < 8; i += 2)
            {
                int newR = r + I[i];
                int newC = c + I[i + 1];
                if(newR >= 0 && newC >= 0 && newR < N && newC < M && !done.get((newR << 9) + newC) )
                {
                    int newRC = (newR << 9) + newC;
                    done.set(newRC);
                    if(palace[newR][newC] == dest)
                    {
                        DP[newR][newC] = tempDP[r][c] + 1;
                        ++dCnt;
                    }
                    tempDP[newR][newC] = tempDP[r][c] + 1;
                    q[t++] = newRC;
                }
            }
        }

    }
}
