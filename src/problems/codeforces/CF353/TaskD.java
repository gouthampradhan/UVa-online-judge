package problems.codeforces.CF353;

import problems.codeforces.CF353.MyScanner;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.TreeSet;

class Node
{
    int value, index;
    Node left, right;
    Node(int value, int index)
    {
        this.value = value;
        this.index = index;
    }
}

public class TaskD
{
    private int[] P;

    public void solve(int testNumber, MyScanner in, PrintWriter out)
    {
        int N = in.nextInt();
        Node[] nodes = new Node[N];
        P = new int[N];
        TreeSet<Node> set = new TreeSet<>(new Comparator<Node>()
        {
            @Override
            public int compare(Node n1, Node n2)
            {
                return (n1.value < n2.value) ? -1 : (n1.value > n2.value) ? 1 : 0;
            }
        });

        Node node = new Node(in.nextInt(), 0);
        set.add(node);

        for(int i = 1; i < N; i++)
        {
            Node child = new Node(in.nextInt(), i);
            Node parent1 = set.lower(child);
            if(parent1 != null && parent1.right == null)
            {
                parent1.right = child;
                set.add(child);
                P[child.index] = parent1.value;
            }
            else
            {
                Node parent2 = set.higher(child);
                parent2.left = child;
                set.add(child);
                P[child.index] = parent2.value;
            }
        }
        for(int i = 1; i < N; i++)
            out.print(P[i] + " ");
    }
}
