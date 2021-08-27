package project4.Models;

public class Variable {

    private int[] possibleValues;
    private Coordinate coordinate;

    public Variable(int value, int x, int y) {
        addValue(value);
        coordinate = new Coordinate(x, y);
    }

    public Variable(int value, Coordinate coordinate) {
        addValue(value);
        setCoordinate(coordinate);
    }

    public int[] getPossibleValues() {
        return possibleValues;
    }

    public boolean addValue(int value) {
        if (!hasValueOf(value)) {
            if (possibleValues != null) {
                int[] temp = new int[possibleValues.length + 1];
                for (int i = 0; i < possibleValues.length; i++) {
                    temp[i] = possibleValues[i];
                }
                temp[temp.length - 1] = value;
                possibleValues = temp;
            } else {
                possibleValues = new int[1];
                possibleValues[0] = value;
            }
            return true;
        }
        return false;
    }

    public boolean hasValueOf(int value) {
        if (possibleValues == null) {
            return false;
        }
        for (int element : possibleValues) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteValue(int value) {
        if (hasValueOf(value)) {
            if (possibleValues.length == 1) {
                possibleValues = null;
            } else {
                int[] temp = new int[possibleValues.length - 1];
                boolean valueIndexReached = false;
                for (int i = 0; i < possibleValues.length; i++) {
                    if (possibleValues[i] != value && !valueIndexReached) {
                        temp[i] = possibleValues[i];
                    } else if (possibleValues[i] != value && valueIndexReached) {
                        temp[i - 1] = possibleValues[i];
                    } else {
                        valueIndexReached = true;
                        continue;
                    }
                }
                possibleValues = temp;
            }
            return true;
        }
        return false;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
