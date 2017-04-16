/*
ID: vincent30
LANG: JAVA
TASK: range
*/

import java.io.*;
import java.util.*;

public class range {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("range.in")));

        int N = Integer.parseInt(in.readLine());
        boolean[][] field = new boolean[N][N];
        for (int i=0; i<N; i++) {
            String s = in.readLine();
            for (int j=0; j<N; j++) {
                field[i][j] = s.charAt(j) == '1';
            }
        }
        in.close();

        // squares[size][row][col] : whether there's a square
        // of SIZE x SIZE with top left corner at (ROW, COL)
        boolean[][][] squares = new boolean[N+1][N][N];
        int[] counts = new int[N+1];
        for (int size=1; size<=N; size++) {
            for (int row=0; row<N-size+1; row++) {
                for (int col=0; col<N-size+1; col++) {
                    if (size == 1)
                        squares[size][row][col] = field[row][col];
                    else
                        squares[size][row][col] = squares[size-1][row][col]
                                            && squares[size-1][row+1][col]
                                            && squares[size-1][row+1][col]
                                            && squares[size-1][row][col+1]
                                            && squares[size-1][row+1][col+1];

                    if (squares[size][row][col])
                        counts[size]++;
                }
            }
        }

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("range.out")));

        for (int size=2; size<=N; size++) {
            if (counts[size] == 0)
                break;

            System.out.println(size + " " + counts[size]);
            out.write(size + " " + counts[size]);
            out.newLine();
        }

        out.close();
    }
}
