package project4.Models;

public class Constraints {

    private int size;
    private int[][] input;
    private int[] maxColoredVariables;

    public void initialize(int[][] input) {
        this.input = input;
        this.size = input.length / 2;
        maxColoredVariables = new int[2 * size];
        for (int i = 0; i < size * 2; i++) {
            maxColoredVariables[i] = 0;
            for (int j = 0; j < input[i].length; j++) {
                maxColoredVariables[i] += input[i][j];
            }
        }
    }

    public boolean satisfy(Board board, Variable variable, int value) {

        int x = variable.getCoordinate().getX();
        int y = variable.getCoordinate().getY();

        int filledVariablesNumber = 0;
        int emptyVariablesNumber = 0;

        Variable[][] map = board.getMap();
        int completeCounter = 0;

        for (int i = 0; i < size; i++) {
            if (i == y) {
                completeCounter++;
                continue;
            }
            if (map[x][i].getPossibleValues().length == 1) {
                completeCounter++;
            }
        }

        for (int i = 0; i < size; i++) {
            if (i == y) {
                if (value == 1) {
                    filledVariablesNumber++;
                } else {
                    emptyVariablesNumber++;
                }
                continue;
            }
            if (map[x][i].getPossibleValues().length == 1 && map[x][i].hasValueOf(1)) {
                filledVariablesNumber++;
            }
            if (map[x][i].getPossibleValues().length == 1 && map[x][i].hasValueOf(0)) {
                emptyVariablesNumber++;
            }
        }

        if (completeCounter == size) {
            if (filledVariablesNumber != maxColoredVariables[x]) {
                return false;
            }
        }

        if (emptyVariablesNumber > size - maxColoredVariables[x] || filledVariablesNumber > maxColoredVariables[x]) {
            return false;
        }

        if (filledVariablesNumber == maxColoredVariables[x]) {
            int counter = 0;
            int k = 0;
            boolean sw = false;
            for (int i = 0; i < size; i++) {
                if (i == y) {
                    if (value == 1) {
                        counter++;
                        if (!sw) {
                            sw = true;
                        }
                    } else {
                        if (sw) {
                            if (getInput()[x][k] == counter) {
                                k++;
                                sw = false;
                            }
                        }
                        counter = 0;
                    }
                    continue;
                }
                if (map[x][i].getPossibleValues().length == 1 && map[x][i].hasValueOf(1)) {
                    counter++;
                    if (!sw) {
                        sw = true;
                    }
                } else {
                    if (sw) {
                        if (getInput()[x][k] == counter) {
                            k++;
                            sw = false;
                        }
                    }
                    counter = 0;
                }
            }
            if (k != getInput()[x].length && !(k == input[x].length - 1 && counter == input[x][k])) {
                return false;
            }
        }

        /**
         * *******************************************************************
         */
        filledVariablesNumber = 0;
        emptyVariablesNumber = 0;
        completeCounter = 0;

        for (int i = 0; i < size; i++) {
            if (i == x) {
                completeCounter++;
                continue;
            }
            if (map[i][y].getPossibleValues().length == 1) {
                completeCounter++;
            }
        }

        for (int i = 0; i < size; i++) {
            if (i == x) {
                if (value == 1) {
                    filledVariablesNumber++;
                } else {
                    emptyVariablesNumber++;
                }
                continue;
            }
            if (map[i][y].getPossibleValues().length == 1 && map[i][y].hasValueOf(1)) {
                filledVariablesNumber++;
            }
            if (map[i][y].getPossibleValues().length == 1 && map[i][y].hasValueOf(0)) {
                emptyVariablesNumber++;
            }
        }

        if (completeCounter == size) {
            if (filledVariablesNumber != maxColoredVariables[y + size]) {
                return false;
            }
        }

        if (emptyVariablesNumber > size - maxColoredVariables[y + size] || filledVariablesNumber > maxColoredVariables[y + size]) {
            return false;
        }

        if (filledVariablesNumber == maxColoredVariables[y + size]) {
            int counter = 0;
            int k = 0;
            boolean sw = false;
            for (int i = 0; i < size; i++) {
                if (i == x) {
                    if (value == 1) {
                        counter++;
                        if (!sw) {
                            sw = true;
                        }
                    } else {
                        if (sw) {
                            if (getInput()[y + size][k] == counter) {
                                k++;
                                sw = false;
                            }
                        }
                        counter = 0;
                    }
                    continue;
                }
                if (map[i][y].getPossibleValues().length == 1 && map[i][y].hasValueOf(1)) {
                    counter++;
                    if (!sw) {
                        sw = true;
                    }
                } else {
                    if (sw) {
                        if (getInput()[y + size][k] == counter) {
                            k++;
                            sw = false;
                        }
                    }
                    counter = 0;
                }
            }
            if (k != getInput()[y + size].length && !(k == input[y + size].length - 1 && counter == input[y + size][k])) {
                return false;
            }
        }
        return true;
    }

    public int[][] getInput() {
        return input;
    }
}
