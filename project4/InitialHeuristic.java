/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4;

import project4.Models.Board;

/**
 *
 * @author Mohammad
 */
public class InitialHeuristic {

    public Board execute(int size, int[][] input, Board board) {
        // Heuristic 2 ~~ beta
        for (int i = 0; i < size * 2; i++) {
            for (int j = 0; j < input[i].length; j++) {
                int realEstate = size;
                int offset = 0;
                for (int k = 0; k < input[i].length; k++) {
                    if (k != j) {
                        realEstate -= input[i][k] + 1;
                    }
                    if (j > k) {
                        offset += input[i][k] + 1;
                    }
                }
//                System.out.println("offset " + offset + " rs " + realEstate);
                heuristic2(input[i][j], realEstate, board, i, offset);
            }
        }
        /*board.print();*/
        return board;
    }

    public static void heuristic2(int number, int size, Board board, int index, int offset) {
        if (number >= Math.ceil((double) size / 2)) {

            int x = number - (int) Math.ceil((double) size / 2);
            if (size % 2 == 0) {
                for (int k = size / 2 + x; k > size / 2 - x; k--) {
                    if (index < board.getSize()) {
                        board.getMap()[index][k - 1 + offset].deleteValue(0);
                    } else {
                        board.getMap()[k - 1 + offset][index - board.getSize()].deleteValue(0);
                    }
                }

            } else {
                for (int k = size / 2 + x; k >= size / 2 - x; k--) {
                    if (index < board.getSize()) {
                        board.getMap()[index][k + offset].deleteValue(0);
                    } else {
                        board.getMap()[k + offset][index - board.getSize()].deleteValue(0);
                    }
                }

            }
        }
    }
}
