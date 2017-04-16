/*
ID: vincent30
LANG: JAVA
TASK: msquare
*/

import java.io.*;
import java.util.*;

public class msquare {
    // how many positions to shift left the nth digit to
    // go from clockwise rotation to integer bit representation
    static final int[] BIT_ORDER = {7, 6, 5, 4, 0, 1, 2, 3};

    static class Pair {
        int board;
        String seq;
        public Pair(int b, String s) {
            board = b;
            seq = s;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("msquare.in")));
        String[] tokens = in.readLine().split(" ");

        // encode each number 1...8 in 4 bits
        // then the entire board of 8 numbers fits in a 32 bit int
        // (32 bits is more convenient than 24 bits)
        int target = 0;
        for (int i=0; i<8; i++) {
            target |= Integer.parseInt(tokens[i]) << (BIT_ORDER[i]*4);
        }
        in.close();

        int start = 0;
        for (int i=1; i<=8; i++) {
            start |= i << (BIT_ORDER[i-1] * 4);
        }

        //printBoard(start);
        //printBoard(transformA(start));
        //printBoard(transformB(start));
        //printBoard(transformC(start));

        // 8! = 40320 which is relatively small
        boolean[] visited = new boolean[8*7*6*5*4*3*2*1];

        // Simple BFS
        Queue<Pair> queue = new LinkedList<Pair>();
        queue.add(new Pair(start, ""));
        String res;
        while (true) {
            Pair entry = queue.poll();

            if (entry.board == target) {
                res = entry.seq;
                break;
            }

            int hash = hash(entry.board);
            if (visited[hash])
                continue;

            visited[hash] = true;

            queue.add(new Pair(transformA(entry.board), entry.seq + "A"));
            queue.add(new Pair(transformB(entry.board), entry.seq + "B"));
            queue.add(new Pair(transformC(entry.board), entry.seq + "C"));
        }

        System.out.println(res.length());
        System.out.println(res);

        BufferedWriter out = new BufferedWriter(new FileWriter(new File("msquare.out")));
        out.write("" + res.length());
        out.newLine();
        out.write(res);
        out.newLine();
        out.close();
    }

    static void printBoard(int board) {
        // print leftmost bits left to right (top row of board)
        for (int i=7; i>=0; i--) {
            int mask = 0xF << (i*4);
            int digit = (board & mask) >>> (i*4);
            System.out.print(digit + " ");

            if (i == 4)
                System.out.println();
        }
        System.out.println("\n");
    }

    /*
     * find hash so 0 <= hash < 8!
     * 1 2 3 4
     * 8 7 6 5
     * has hash 0
     *
     * 8 7 6 5
     * 1 2 3 4
     * has hash 8! - 1
     *
     * essentially, for a sequence like 2 6 8 4 1 3 7 5
     * find n such that the sequence is the nth permutation,
     * 0 indexed
     *
     * normally we calculate left to right:
     * 2     6     8     4     1     3     7     5
     * 1*7!  4*6!  5*5!  2*4!  0*3!  0*2!  1*1!  0*0!
     *
     * but notice how the reverse sequence,
     * 0 1 0 0 2 5 2 4
     * are the indices to insert
     * 5 7 3 1 4 8 6 2
     * to maintain sorted order
     *
     * HashSet is actually probably faster than this
     */
    static int hash(int board) {
        LinkedList<Integer> digits = new LinkedList<Integer>();
        int hash = 0, fact = 1;
        for (int i=1; i<=8; i++) {

            // extract next digit
            int num = board & 0xF;

            // find index to insert
            int toInsert = 0;
            ListIterator<Integer> iter = digits.listIterator();
            while (iter.hasNext()) {
                int curr = iter.next();

                // move cursor back so we insert in right place
                if (curr > num) {
                    iter.previous();
                    break;
                }

                toInsert++;
            }
            // do insert with iterator to be efficient
            iter.add(num);

            //System.out.printf("digit = %d toInsert = %d list = %s fact = %d\n",
            //        num, toInsert, digits, i);

            hash += toInsert * fact;
            fact *= i;
            board >>>= 4;   // want logical right shift
        }
        return hash;
    }

    // swap rows
    static int transformA(int board) {
        return board << 4*4 | board >>> 4*4;
    }

    // shift both rows right
    static int transformB(int board) {
        return (board & 0xFFF0FFF0) >>> 1*4
             | (board & 0x000F000F) << 3*4;
    }

    // rotate center 4 squares clockwise
    static int transformC(int board) {
        return board & 0xF00FF00F
           | (board & 0x0F000000) >>> 1*4
           | (board & 0x00F00000) >>> 4*4
           | (board & 0x00000F00) << 4*4
           | (board & 0x000000F0) << 1*4;
    }
}
