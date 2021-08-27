package algorithems;

import models.State;
import models.StateValue;
import models.StateValue2;

import java.util.*;

public class RBFS {
    public static Comparator<StateValue> idComparator = new Comparator<StateValue>(){

        @Override
        public int compare(StateValue c1, StateValue c2) {
            return (int) (c1.getHuristicValue() - c2.getHuristicValue());
        }
    };
    public static void solve(State start) {

        int count = 0;
        int cutOff;


//        if (start.isFinal()) {
//            start.print();
//            return start;
//        }

        Set<State> visitedList = new TreeSet<>();
//        visitedList.add(start);
        ArrayList<StateValue2> fringe = new ArrayList<>();
        fringe.add(new StateValue2(start, start.getHuristic(), Integer.MIN_VALUE));
        int min = fringe.get(0).getHuristicValue();

        for(int jjj=0; jjj<Integer.MAX_VALUE; jjj++){

            visitedList.clear();
            //________________________


            cutOff = min;
            min=Integer.MAX_VALUE;
            fringe.clear();
            fringe.add(new StateValue2(start, start.getHuristic()));

            while (!fringe.isEmpty()) {
                StateValue2 temp = fringe.remove(0);
                StateValue2 temp2 = fringe.get(0);
                if(temp.getState().getHuristic()<=temp.getSecondBestValue()){
                    count++;
                    if (temp.getState().isFinal()) {

                        temp.getState().print();
                        System.out.println("node count = " + count);
                        return;
                    }
                    for (State child : temp.getState().makeChild()) {
                        if (!visitedList.contains(child)) {

                            visitedList.add(temp.getState());

                            fringe.add(new StateValue2(child, child.getHuristic()));
                        }
                    }
                } else {
//                    if(temp.getHuristic()<min){
//                        min = temp.getHuristic();
//                    }
                }


                Collections.sort(fringe);

//            for(int a=0; a<fringe.size(); a++){
//                System.out.print(fringe.get(a).getHuristicValue() + " ");
//            }
//            System.out.println('\n');


            }
        }


        System.out.println("not found");

    }






}

