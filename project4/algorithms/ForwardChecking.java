/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4.algorithms;

import project4.Models.Board;
import project4.Models.Constraints;
import project4.Models.Variable;

/**
 *
 * @author Mohammad
 */
public class ForwardChecking {

    public boolean execute(Board board, Constraints constraints, Variable variable, int value, boolean[][] result) {
        boolean noPossibleValues = false;
        for (int i = 0; i < board.getSize(); i++) {
            if (!constraints.satisfy(board, board.getMap()[variable.getCoordinate().getX()][i], 0) && i != variable.getCoordinate().getY()) {
                board.getMap()[variable.getCoordinate().getX()][i].deleteValue(0);
                if (board.getMap()[variable.getCoordinate().getX()][i].getPossibleValues() != null && board.getMap()[variable.getCoordinate().getX()][i].getPossibleValues().length == 1) {
                    result[variable.getCoordinate().getX()][i] = true;
                }
            }
            if (board.getMap()[variable.getCoordinate().getX()][i].getPossibleValues() == null || board.getMap()[variable.getCoordinate().getX()][i].getPossibleValues().length == 0) {
                noPossibleValues = true;
                break;
            }
            if (!constraints.satisfy(board, board.getMap()[i][variable.getCoordinate().getY()], 0) && i != variable.getCoordinate().getX()) {
                board.getMap()[i][variable.getCoordinate().getY()].deleteValue(0);
                if (board.getMap()[i][variable.getCoordinate().getY()].getPossibleValues() != null && board.getMap()[i][variable.getCoordinate().getY()].getPossibleValues().length == 1) {
                    result[i][variable.getCoordinate().getY()] = true;
                }
            }
            if (board.getMap()[i][variable.getCoordinate().getY()].getPossibleValues() == null || board.getMap()[i][variable.getCoordinate().getY()].getPossibleValues().length == 0) {
                noPossibleValues = true;
                break;
            }
            if (!constraints.satisfy(board, board.getMap()[variable.getCoordinate().getX()][i], 1) && i != variable.getCoordinate().getY()) {
                board.getMap()[variable.getCoordinate().getX()][i].deleteValue(1);
                result[variable.getCoordinate().getX()][i] = false;
            }
            if (board.getMap()[variable.getCoordinate().getX()][i].getPossibleValues() == null || board.getMap()[variable.getCoordinate().getX()][i].getPossibleValues().length == 0) {
                noPossibleValues = true;
                break;
            }
            if (!constraints.satisfy(board, board.getMap()[i][variable.getCoordinate().getY()], 1) && i != variable.getCoordinate().getX()) {
                board.getMap()[i][variable.getCoordinate().getY()].deleteValue(1);
                result[i][variable.getCoordinate().getY()] = false;
            }
            if (board.getMap()[i][variable.getCoordinate().getY()].getPossibleValues() == null || board.getMap()[i][variable.getCoordinate().getY()].getPossibleValues().length == 0) {
                noPossibleValues = true;
                break;
            }
        }
        return !noPossibleValues;
    }
}
