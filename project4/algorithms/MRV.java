package project4.algorithms;

import project4.Models.Board;
import project4.Models.Variable;

public class MRV {

    public Variable execute(Board board, boolean[][] result) {
        Variable[][] map = board.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].getPossibleValues().length == 1 && map[i][j].hasValueOf(1) && result[i][j] == false) {
                    return map[i][j];
                }
                if (map[i][j].getPossibleValues().length == 1 && map[i][j].hasValueOf(0) && result[i][j] == true) {
                    return map[i][j];
                }
            }
        }
        return null;
    }
}
