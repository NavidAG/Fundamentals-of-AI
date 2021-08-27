package project4.algorithms;

import project4.Models.Board;
import project4.Models.Constraints;
import project4.Models.Variable;

public class MainAlgorithm {

    private int counter;

    public boolean[][] backtracking(Board board, Constraints constraints) {
        boolean[][] result = new boolean[board.getSize()][board.getSize()];
        for (int i = 0; i < result.length; i++) {
            result[i] = new boolean[result.length];
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = false;
            }
        }
        return recursiveBacktracking(board, constraints, result);
    }

    public boolean[][] recursiveBacktracking(Board board, Constraints constraints, boolean[][] result) {
        counter++;
        if (board.isComplete()) {
            return result;
        }
        Variable variable = board.chooseVariable(result, constraints.getInput());
        int[] values = LCV.execute(board, variable, constraints, result);
        Board board1 = new Board(board.getSize());
        for (int i = 0; i < values.length; i++) {
            board1.setMap(board.getMap());
            if (constraints.satisfy(board1, variable, values[i])) {
                board1.concat(variable, values[i], result);
                if (counter % (Math.pow(10, board.getSize()/5)) == 0) {
                    ArcConsistency arcConsistency = new ArcConsistency();
                    arcConsistency.initialize(board1, constraints, result, variable, values[i]);
                    if (arcConsistency.execute()) {
                        boolean[][] tempResult = recursiveBacktracking(board1, constraints, result);
                        if (tempResult != null) {
                            result = tempResult;
                            return result;
                        }
                    }
                } else if (new ForwardChecking().execute(board1, constraints, variable, values[i], result)) {
                    boolean[][] tempResult = recursiveBacktracking(board1, constraints, result);
                    if (tempResult != null) {
                        result = tempResult;
                        return result;
                    }
                }
                board1.separate(variable, values[i], result);
            }
        }
        return null;
    }
}
