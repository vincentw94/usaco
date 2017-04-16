/*
ID: vincent30
LANG: JAVA
TASK: fence
*/

import java.io.*;
import java.util.*;

public class fence {
    static class FenceInfo implements Comparable<FenceInfo> {
        int to, id;
        public FenceInfo(int t, int i) {
            to = t;
            id = i;
        }

        // sort by end node
        @Override
        public int compareTo(FenceInfo other) {
            // allow multiple fences between the same intersections
            if (to != other.to)
                return to - other.to;
            return id - other.id;
        }
    }

    // avoid compiler warning, and keep sorted by end node
    static class AdjList extends TreeSet<FenceInfo> {};

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("fence.in")));
        AdjList[] adj = new AdjList[501];  // start from index 1 since input does
        for (int i=1; i<adj.length; i++) {
            adj[i] = new AdjList();
        }

        int numFences = Integer.parseInt(in.readLine());

        for (int i=0; i<numFences; i++) {
            String[] tokens = in.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            adj[from].add(new FenceInfo(to, i));
            adj[to].add(new FenceInfo(from, i));
        }

        in.close();

        int oddCount = 0;
        int minOdd = Integer.MAX_VALUE, min = Integer.MAX_VALUE;
        for (int node=1; node<=500; node++) {
            // ignore nodes (intersections) not connected to anything
            if (adj[node].isEmpty())
                continue;

            if (adj[node].size() % 2 == 1) {
                System.out.println(node + " is odd");
                oddCount++;
                minOdd = Math.min(minOdd, node);
            }
            min = Math.min(min, node);
        }

        // if odd number of nodes of odd degree, or more than, impossible
        // spec doesn't say how to handle
        if (oddCount > 2 || oddCount % 2 == 1) {
            System.out.println("none");
            return;
        }

        // if 2 odd nodes, start at smaller one
        // else (0 odd) start at global smallest one
        int start = (oddCount == 2) ? minOdd : min;
        boolean[] traversed = new boolean[numFences];
        LinkedList<Integer> path = new LinkedList<Integer>();

        genPath(start, adj, traversed, path);

        System.out.println(path);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("fence.out")));
        for (int i : path) {
            out.write("" + i);
            out.newLine();
        }
        out.close();
    }

    static void genPath(int curr, AdjList[] adj, boolean[] traversed, LinkedList<Integer> path) {
        for (FenceInfo dest : adj[curr]) {
            if (!traversed[dest.id]) {
                traversed[dest.id] = true;
                genPath(dest.to, adj, traversed, path);
            }
        }

        // after recursing, put at beginning of list
        path.add(0, curr);
    }
}
