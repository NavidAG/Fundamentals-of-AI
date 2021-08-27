package models;

public class StateValue implements Comparable{
    private State state;
    private int huristicValue;

    public State getState() {
        return state;
    }

    public int getHuristicValue() {
        return huristicValue;
    }

    public StateValue(State state, int huristicValue) {
        this.state = state;
        this.huristicValue = huristicValue;
    }



    @Override
    public int compareTo(Object o) {
        int compareH=((StateValue)o).getHuristicValue();
        /* For Ascending order*/
        return this.huristicValue-compareH;
    }
}
