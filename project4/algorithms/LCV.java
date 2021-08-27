
package project4.algorithms;

import project4.Models.Board;
import project4.Models.Constraints;
import project4.Models.Variable;


public class LCV {

    public static int[] execute(Board board, Variable variable, Constraints constraints, boolean[][] result) {
        int[] values = variable.getPossibleValues();
        int x = variable.getCoordinate().getX();
        int y = variable.getCoordinate().getY();
        int size = board.getSize();
        int totalTrueOf0=0;
        int totalTrueOf1=0;

        
        if(values.length==2){
//            System.out.println("hi");
            for (int k = 0; k < values.length; k++) {
                if (constraints.satisfy(board, variable, values[k])) {
                    board.concat(variable, values[k], result);
                        for(int i=0; i<size; i++){
                            if(i!=y){
                                for(int j=0; j<board.getMap()[x][i].getPossibleValues().length; j++){
                                    if(constraints.satisfy(board, board.getMap()[x][i], board.getMap()[x][i].getPossibleValues()[j])){
                                        if(values[k]==0)
                                            totalTrueOf0++;
                                        else
                                            totalTrueOf1++;
                                    }   
                                }
                            }
                        }
                        for(int i=0; i<size; i++){
                            if(i!=x){
                                for(int j=0; j<board.getMap()[i][y].getPossibleValues().length; j++){
                                    if(constraints.satisfy(board, board.getMap()[i][y], board.getMap()[i][y].getPossibleValues()[j])){
                                       if(values[k]==0)
                                            totalTrueOf0++;
                                        else
                                            totalTrueOf1++;
                                    }   
                                }
                            }
                        }

                    board.separate(variable, values[k], result);
                }
            }
        }
        
        if(totalTrueOf0>totalTrueOf1){
            values[0] = 0;
            values[1] = 1;
        }
        if(totalTrueOf0<totalTrueOf1){
            values[0] = 1;
            values[1] = 0;
        }
        return values;
//            System.err.println("totalTrue: " + totalTrue);
//            System.err.println("x: " + x);
//            System.err.println("y: " + y);
    }
}
