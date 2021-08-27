package algorithems;

import models.State;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class BDS {
    public static void solve(State start, State finish) {
        int count = 0;
        Queue<State> fringe1 = new LinkedList<>();
        Queue<State> fringe2 = new LinkedList<>();
        Set<State> visitedList1 = new TreeSet<>();
        Set<State> visitedList2 = new TreeSet<>();
        fringe1.add(start);
        fringe2.add(finish);
        visitedList1.add(start);
        visitedList2.add(finish);
        while(!fringe1.isEmpty() && !fringe2.isEmpty()){
            State temp1 = fringe1.poll();
            State temp2 = fringe2.poll();
            if(!fringe1.isEmpty()){
               count++;
               if(fringe2.contains(temp1)){
                   temp1.print();
                   System.out.println("node count = " + count);
                   return;
               }
               for (State child: temp1.makeChild()){
                   if(!visitedList1.contains(child)){
                       visitedList1.add(child);
                       fringe1.add(child);
                   }
               }
            }
            if(!fringe2.isEmpty()){
                count++;
                if(fringe1.contains(temp2)){
                    temp2.print();
                    System.out.println("node count = " + count);
                    return;
                }
                for (State child: temp2.makeChild()){
                    if(!visitedList2.contains(child)){
                        visitedList2.add(child);
                        fringe2.add(child);
                    }
                }
            }
        }

    }
}
