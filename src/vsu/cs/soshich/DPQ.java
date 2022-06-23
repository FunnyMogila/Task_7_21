package vsu.cs.soshich;

import java.util.*;
public class DPQ {
    private int dist[];
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    private int V; // Number of vertices
    List<List<Node> > adj;

    public DPQ(int V)
    {
        this.V = V;
        dist = new int[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
    }

    public void dijkstra(List<List<Node> > adj, int src)
    {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Add source node to the priority queue
        pq.add(new Node(src, 0));

        // Distance to the source is 0
        dist[src] = 0;
        while (settled.size() != V) {
            //when the priority queue is empty, return
            if(pq.isEmpty())
                return ;
            // remove the minimum distance node
            // from the priority queue
            int u = pq.remove().node;

            // adding the node whose distance is
            // finalized
            settled.add(u);

            e_Neighbours(u);
        }
    }

    private void e_Neighbours(int u)
    {
        int edgeDistance = -1;
        int newDistance = -1;

        // All the neighbors of v
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);

            // If current node hasn't already been processed
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                // If new distance is cheaper in cost
                if (newDistance < dist[v.node])
                    dist[v.node] = newDistance;

                // Add the current node to the queue
                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }

    public static void main(String arg[])
    {
        int V = 9;
        int N = 3;
        ArrayList<Integer> rightList = new ArrayList<>();
        int[][] matrixOfWays = new int[V][V];

        for (int i = 0; i < V; i++)
        {
            for (int j = 0; j < V; j++)
            {
                matrixOfWays[i][j] = 0;
            }
        }

        List<List<Node> > adj = new ArrayList<List<Node> >();

        for (int i = 0; i < V; i++)
        {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        addEdge(0, 4, adj, matrixOfWays);
        addEdge(1, 5, adj, matrixOfWays);
        addEdge(2, 5, adj, matrixOfWays);
        addEdge(3, 6, adj, matrixOfWays);
        addEdge(4, 5, adj, matrixOfWays);
        addEdge(5, 6, adj, matrixOfWays);
        addEdge(5, 7, adj, matrixOfWays);
        addEdge(7, 8, adj, matrixOfWays);

        for (int j = 0; j < V; j++)
        {
            DPQ dpq = new DPQ(V);
            dpq.dijkstra(adj, j);

            System.out.println("Кратчайшие пути от вершины " + j + ":");
            for (int i = 0; i < dpq.dist.length; i++)
            {
                System.out.println("до " + i + " = " + dpq.dist[i]);
            }
            System.out.println();

            boolean flag = true;
            for (int i = 0; i < dpq.dist.length; i++)
            {
                if (dpq.dist[i] > N)
                {
                    flag = false;
                }
            }
            if (flag)
            {
                rightList.add(j);
            }
        }

        System.out.println("Узлы, удовлетворяющие теории N рукопожатий:");
        for (int i = 0; i < rightList.size(); i++)
        {
            System.out.print(rightList.get(i) + " ");
        }
        System.out.println();
        System.out.println("\nМатрица смежности:");
        System.out.print("    ");
        for (int i = 0; i < V; i++)
        {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < V * 2 + 3; i++)
        {
            System.out.print("_");
        }
        System.out.println();
        for (int i = 0; i < V; i++)
        {
            System.out.print(i + " | ");
            for (int j = 0; j < V; j++)
            {
                System.out.print(matrixOfWays[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void addEdge(int from, int to, List<List<Node>> adj, int[][] matrixOfWays)
    {
        adj.get(from).add(new Node(to, 1));
        adj.get(to).add(new Node(from, 1));

        matrixOfWays[from][to] = 1;
        matrixOfWays[to][from] = 1;
    }
}

class Node implements Comparator<Node> {
    public int node;
    public int cost;

    public Node()
    {
    }

    public Node(int node, int cost)
    {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2)
    {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        return 0;
    }
}
