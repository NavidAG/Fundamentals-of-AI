package project4.Models;

import project4.algorithms.MRV;

public class Board {

    private int size;
    private Variable map[][];

    //this function may be removed in the future
    public void setMap(int i, int j, int value) {
        if (value >= 0 && value <= 1) {
            map[i][j] = new Variable(value, i, j);
        }
    }

    public void setMap(Variable[][] map) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.map[i][j] = new Variable(map[i][j].getPossibleValues()[0], i, j);
                if (map[i][j].getPossibleValues().length == 2) {
                    this.map[i][j].addValue(map[i][j].getPossibleValues()[1]);
                }
            }
        }
    }

    public Board(int size) {
        this.size = size;
        map = new Variable[size][];
        for (int i = 0; i < map.length; i++) {
            map[i] = new Variable[size];
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Variable(0, i, j);
                map[i][j].addValue(1);
            }
        }
    }

    public void print() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (map[i][j].hasValueOf(1) && map[i][j].getPossibleValues().length == 1) {
                    System.out.print("* ");
                } else if (map[i][j].hasValueOf(0) && map[i][j].getPossibleValues().length == 1) {
                    System.out.print(". ");
                } else if (map[i][j].getPossibleValues().length == 2) {
                    System.out.print("& ");
                } else {
                    System.out.print("! ");
                }
            }
            System.out.println();
        }
        System.out.println("");
    }

    public boolean isComplete() {
        for (Variable[] row : map) {
            for (Variable variable : row) {
                if (!((variable.getPossibleValues().length == 1 && variable.hasValueOf(0))
                        || (variable.getPossibleValues().length == 1 && variable.hasValueOf(1)))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Variable chooseVariable(boolean[][] result, int[][] input) {
        Variable variable = new MRV().execute(this, result);
        if (variable != null) {
            return variable;
        } else {
            /*return additionalHeuristic(input);*/
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (map[i][j].getPossibleValues().length == 2) {
                        return map[i][j];
                    }
                }
            }
            return null;
        }
    }

    public boolean concat(Variable variable, int value, boolean[][] result) {
        int x = variable.getCoordinate().getX();
        int y = variable.getCoordinate().getY();
        if (map[x][y].getPossibleValues().length != 1) {
            map[x][y].deleteValue(0);
            map[x][y].deleteValue(1);
            map[x][y].addValue(value);
            if (value == 1) {
                result[x][y] = true;
            } else if (value == 0) {
                result[x][y] = false;
            }
            return true;
        } else {
            //System.err.println("Alert!");
            map[x][y].deleteValue(0);
            map[x][y].deleteValue(1);
            map[x][y].addValue(value);
            if (value == 1) {
                result[x][y] = true;
            } else if (value == 0) {
                result[x][y] = false;
            }
            return false;
        }
    }

    public void separate(Variable variable, int value, boolean[][] result) {
        int x = variable.getCoordinate().getX();
        int y = variable.getCoordinate().getY();
        result[x][y] = false;
        if (value == 0) {
            map[x][y].addValue(1);
        } else if (value == 1) {
            map[x][y].addValue(0);
        } else {
            System.err.println("Unpredictable!");
        }
    }

    public Variable[][] getMap() {
        return map;
    }

    public Variable additionalHeuristic(int[][] input) {
        int[] roomsAvailable = new int[size * 2];
        for (int i = 0; i < size * 2; i++) {
            roomsAvailable[i] = 0;
        }
        for (int i = 0; i < size * 2; i++) {
            for (int j = 0; j < input[i].length; j++) {
                roomsAvailable[i] += input[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            int filledVariablesCounter = 0;
            for (int j = 0; j < size; j++) {
                if (map[i][j].getPossibleValues().length == 1 && map[i][j].hasValueOf(1)) {
                    filledVariablesCounter++;
                }
            }
            roomsAvailable[i] -= filledVariablesCounter;
        }
        for (int i = 0; i < size; i++) {
            int filledVariablesCounter = 0;
            for (int j = 0; j < size; j++) {
                if (map[j][i].getPossibleValues().length == 1 && map[j][i].hasValueOf(1)) {
                    filledVariablesCounter++;
                }
            }
            roomsAvailable[size + i] -= filledVariablesCounter;
        }
        int maxRow = 0;
        int maxRowIndex = 0;
        int maxColumn = 0;
        int maxColumnIndex = 0;
        for (int i = 0; i < size; i++) {
            if (maxRow < roomsAvailable[i]) {
                maxRow = roomsAvailable[i];
                maxRowIndex = i;
            }
            if (maxColumn < roomsAvailable[size + i]) {
                maxColumn = roomsAvailable[size + i];
                maxColumnIndex = i;
            }
        }
        if (map[maxRowIndex][maxColumnIndex].getPossibleValues().length == 2) {
            return map[maxRowIndex][maxColumnIndex];
        } else if (maxRowIndex >= maxColumnIndex) {
            for (int i = 0; i < size; i++) {
                if (map[maxRowIndex][i].getPossibleValues().length == 2) {
                    return map[maxRowIndex][i];
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (map[i][maxColumnIndex].getPossibleValues().length == 2) {
                    return map[i][maxColumnIndex];
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j].getPossibleValues().length == 2) {
                    return map[i][j];
                }
            }
        }
        return null;
    }
    //when a unit of a board is colored, we assign 1/true to that element of the array
    //when a unit of a board is NOT colored, we assign 0/false to that element of the array

    public int getSize() {
        return size;
    }
}
