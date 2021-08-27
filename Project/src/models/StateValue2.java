package models;

public class StateValue2 implements Comparable{
    private State state;
    private int huristicValue;
    private int secondBestValue;

    public int getSecondBestValue() {
        return secondBestValue;
    }

    public void setSecondBestValue(int secondBestValue) {
        this.secondBestValue = secondBestValue;
    }

    public State getState() {
        return state;
    }

    public int getHuristicValue() {
        return huristicValue;
    }

    public StateValue2(State state, int huristicValue, int secondBestValue) {
        this.state = state;
        this.huristicValue = huristicValue;
        this.secondBestValue = secondBestValue;
    }
    public StateValue2(State state, int huristicValue) {
        this.state = state;
        this.huristicValue = huristicValue;
    }



    @Override
    public int compareTo(Object o) {
        int compareH=((StateValue2)o).getHuristicValue();
        /* For Ascending order*/
        return this.huristicValue-compareH;
    }
}
