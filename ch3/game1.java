/*
ID: vincent30
LANG: JAVA
TASK: game1
*/

import java.io.*;
import java.util.*;

public class game1 {

    static int total(int[] cumSum, int start, int end) {
        return cumSum[end+1] - cumSum[start];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("game1.in")));
        int N = Integer.parseInt(in.readLine());
        int[] board = new int[N];
        int[] cumSum = new int[N+1];  // cumulative sum

        int i = 0;
        while (i < N) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            while (st.hasMoreTokens()) {
                board[i] = Integer.parseInt(st.nextToken());
                cumSum[i+1] = cumSum[i] + board[i];
                i++;
            }
        }
        in.close();

        // max score for start...end, inclusive
        int[][] best = new int[N][N];
        for (int len=1; len<=N; len++) {
            for (int start=0; start<N - len + 1; start++) {
                int end = start + len - 1;
                if (len == 1)
                    best[start][end] = board[start];
                else {
                    int score1 = board[start] + total(cumSum, start+1, end) - best[start+1][end];
                    int score2 = board[end] + total(cumSum, start, end-1) - best[start][end-1];
                    best[start][end] = Math.max(score1, score2);
                }
            }
        }

        int p1 = best[0][N-1];
        int p2 = Math.min(best[1][N-1], best[0][N-2]);

        System.out.println(p1 + " " + p2);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("game1.out")));
        out.write(p1 + " " + p2);
        out.newLine();
        out.close();
    }
}
