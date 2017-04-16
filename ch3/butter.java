/*
ID: vincent30
LANG: JAVA
TASK: butter
*/

import java.io.*;
import java.util.*;

public class butter {

    static class AdjInfo {
        int to, dist;
        public AdjInfo(int to_, int dist_) {
            to = to_;
            dist = dist_;
        }
    }

    // to avoid compile warning
    static class AdjacencyList extends LinkedList<AdjInfo> {}

    static void ucs(int start, AdjacencyList[] adj, int[] output) {
        // in case some pastures unreachable
        // from all cows
        for (int i=0; i<output.length; i++)
            output[i] = Integer.MAX_VALUE;

        boolean[] visited = new boolean[adj.length];
        PriorityQueue<AdjInfo> queue = new PriorityQueue<AdjInfo>(new Comparator<AdjInfo>() {
            public int compare(AdjInfo a1, AdjInfo a2) {
                return a1.dist - a2.dist;
            }
        });
        queue.add(new AdjInfo(start, 0));

        while (!queue.isEmpty()) {
            AdjInfo curr = queue.poll();
            if (visited[curr.to])
                continue;

            visited[curr.to] = true;
            output[curr.to] = curr.dist;

            for (AdjInfo next : adj[curr.to]) {
                if (!visited[next.to])
                    queue.add(new AdjInfo(next.to, curr.dist + next.dist));
            }
        }

    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("butter.in")));

        // N: number of cows
        // P: number of pastures
        // C: number of paths
        String[] npc = in.readLine().split(" ");
        int N = Integer.parseInt(npc[0]);
        int P = Integer.parseInt(npc[1]);
        int C = Integer.parseInt(npc[2]);

        // read cow starting pastures - subtract 1 to get 0 index
        int[] cows = new int[N];
        for (int i=0; i<N; i++)
            cows[i] = Integer.parseInt(in.readLine()) - 1;

        // build adjacency lists
        AdjacencyList[] adjacent = new AdjacencyList[P];
        for (int i=0; i<P; i++) {
            adjacent[i] = new AdjacencyList();
        }
        for (int i=0; i<C; i++) {
            // subtract 1 to get 0 indexing for pastures
            String[] pathInfo = in.readLine().split(" ");
            int from = Integer.parseInt(pathInfo[0]) - 1;
            int to = Integer.parseInt(pathInfo[1]) - 1;
            int dist = Integer.parseInt(pathInfo[2]);

            adjacent[from].add(new AdjInfo(to, dist));
            adjacent[to].add(new AdjInfo(from, dist));
        }
        in.close();

        // shortest distances from each cow to each pasture
        int[][] shortestPaths = new int[N][P];

        // sum of shortest distances to pastures
        int[] totalDists = new int[P];

        for (int i=0; i<N; i++) {
            ucs(cows[i], adjacent, shortestPaths[i]);

            // update total distances as we calculate shortest paths
            for (int p=0; p<P; p++) {
                if (shortestPaths[i][p] == Integer.MAX_VALUE)
                    totalDists[p] = Integer.MAX_VALUE;
                else if (shortestPaths[i][p] != Integer.MAX_VALUE)
                    totalDists[p] += shortestPaths[i][p];
            }
        }

        // find smallest total distance
        int best = Integer.MAX_VALUE;
        for (int total : totalDists)
            best = Math.min(best, total);

        System.out.println(best);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("butter.out")));
        out.write("" + best);
        out.newLine();
        out.close();
    }
}
