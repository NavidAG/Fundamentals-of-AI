package algorithems;

import models.State;

import java.util.Set;
import java.util.TreeSet;

public class IDS2 {
    static Set<State> visitedList = new TreeSet<>();
    static int minin = Integer.MAX_VALUE;
    public static void solve(State start, int count) {


        if (start.isFinal()) {
            if(minin>count) {
                minin=count;
            }
            start.print();
            System.out.println(minin);
            return;

        }
//        System.out.println(count);
        visitedList.add(start);
        for (State child : start.makeChild()) {
           if(!visitedList.contains(child) && count<minin){
               solve(child, count+1);
           }

        }
    }
}
