package algorithems;

import models.State;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class IDS {

    public static void solve(State start) {
        int depth = 0;
        int count = 0;

        if (start.isFinal()) {
            start.print();
            return;
        }



        Set<State> visitedList = new TreeSet<>();
        Stack<State> fringe = new Stack<>();

        for(int iii=0; iii<Integer.MAX_VALUE; iii++){
            visitedList.clear();
            fringe.clear();
            visitedList.add(start);
            fringe.push(start);
            depth++;
            count = 0;
            while (!fringe.isEmpty()) {

                    State temp = fringe.pop();
                    if(temp.getDepth()<=depth) {


                        count++;
                        if (temp.isFinal()) {
                            temp.print();
                            System.out.println("node count = " + count);
                            return;
                        }

                        for (State child : temp.makeChild()) {
                            if (!visitedList.contains(child)) {
                                visitedList.add(child);
                                fringe.push(child);
                            }

                        }
                    }


            }
        }




        System.out.println("not found");

    }
}
