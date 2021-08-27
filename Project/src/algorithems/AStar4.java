package algorithems;

import models.State;
import models.StateValue;

import java.util.*;

public class AStar4 {
    public static Comparator<StateValue> idComparator = new Comparator<StateValue>(){

        @Override
        public int compare(StateValue c1, StateValue c2) {
            return (int) (c1.getHuristicValue() - c2.getHuristicValue());
        }
    };
    public static State solve(State start) {

        int count = 0;


//        if (start.isFinal()) {
//            start.print();
//            return start;
//        }

        Set<State> visitedList = new TreeSet<>();
        visitedList.add(start);
        ArrayList<StateValue> fringe = new ArrayList<>();
        fringe.add(new StateValue(start, start.getHuristic()));


        while (!fringe.isEmpty()) {

            State temp = fringe.remove(0).getState();

            count++;
            if (temp.isFinal()) {

                temp.print();
                System.out.println("node count = " + count);
                return temp;
            }
//            visitedList.add(start);

//            if(!visitedList.contains(temp)){
//                count++;
//            }
            for (State child : temp.makeChild()) {

                if (!visitedList.contains(child)) {

                    visitedList.add(child);
                    fringe.add(new StateValue(child, child.getHuristic()));
                }
            }

            Collections.sort(fringe);
//            for(int a=0; a<fringe.size(); a++){
//                System.out.print(fringe.get(a).getHuristicValue() + " ");
//            }
//            System.out.println('\n');


        }

        System.out.println("not found");
        return null;
    }






}

