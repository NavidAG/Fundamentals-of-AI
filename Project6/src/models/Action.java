package models;

public class Action implements Comparable{

    @Override
    public int compareTo(Object o) {
        int compareAction=((Action)o).getEvaluationValue();
        return compareAction-this.getEvaluationValue();
    }



    public enum ActionType {
        reinforce, attack, nothing
    }

    private final ActionType type;
    private final Board.BoardCell start;
    private final Board.BoardCell target;
    private int evaluationValue;

    public int getEvaluationValue() {
        return evaluationValue;
    }

    public void setEvaluationValue(int evaluationValue) {
        this.evaluationValue = evaluationValue;
    }

    public Action(ActionType type, Board.BoardCell start, Board.BoardCell target) {
        this.type = type;
        this.start = start;
        this.target = target;
    }

    public ActionType getType() {
        return type;
    }

    public Board.BoardCell getStart() {
        return start;
    }

    public Board.BoardCell getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Action{" +
                "type=" + type +
                ", start=" + start +
                ", target=" + target +
                '}';
    }
}
