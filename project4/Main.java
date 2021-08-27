package project4;

import project4.Models.Board;

import java.util.Scanner;
import project4.Models.Constraints;
import project4.algorithms.MainAlgorithm;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello");
        int size = 6;
        Scanner in = new Scanner(System.in);
        size = in.nextInt();
        Board board = new Board(size);
        int[][] input = new int[size * 2][];

        for (int i = 0; i < size * 2; i++) {
            int count = in.nextInt();
            input[i] = new int[count];
            for (int j = 0; j < count; j++) {
                input[i][j] = in.nextInt();
            }
        }
       /* for (int i = 0; i < size * 2; i++) {
            for (int j = 0; j < input[i].length; j++) {
//                System.out.print(input[i][j] + " ");
            }
            System.out.println();
        }*/
        new InitialHeuristic().execute(size, input, board);
        Constraints constraints = new Constraints();
        constraints.initialize(input);
        boolean[][] result = new MainAlgorithm().backtracking(board, constraints);
        /*board.print();*/
        for (boolean[] row : result) {
            for (boolean element : row) {
                if (element == true) {
                    System.out.print("* ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("");
        }
    }
}
