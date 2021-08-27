import models.*;

import java.util.ArrayList;
import java.util.Collections;

public class Ai extends Player {

    private int doneActions = 0;
    private final int maxDepth = 4;

    public Ai(PlayerType type) {
        super(type);
    }

    @Override
    public Action forceAttack(Game game) {
        int maxValue = Integer.MIN_VALUE;
        Action bestAction = null;
        ArrayList<Action> actions = getAllActions(game.getBoard());
//        for(int i=0; i<actions.size(); i++){
//            if(actions.get(i).getType() == Action.ActionType.attack)
//                actions.remove(i);
//        }
        actions = evaluateState(game, actions);

        if (doneActions == 0 && getType() == PlayerType.white) {
                System.out.println("branch factor(black): "+actions.size());

            for (Action action : actions) {
                if (action.getType() == Action.ActionType.attack) {
                    Game copyGame = game.copy();
                    //-------------------------------

                    if (copyGame.applyActionTwo(this, action, true)) {
                        continue;
                    }
                    Player winner = copyGame.getWinner();
                    if (winner != null) {
                        if (winner.getType() == getType()) {
                            return action;
                        }
                    } else {
                        int temp = Math.max(maxValue, minForceAttack(copyGame, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
                        if (temp > maxValue) {
                            maxValue = temp;
                            bestAction = action;
                        }
                    }
                }
            }
        } else {
            System.out.println("branch factor(black): "+actions.size());
            for (Action action : actions) {
                if (action.getType() == Action.ActionType.attack) {
                    Game copyGame = game.copy();
                    if (copyGame.applyActionTwo(this, action, true)) {
                        continue;
                    }
                    Player winner = copyGame.getWinner();
                    if (winner != null) {
                        if (winner.getType() == getType()) {
                            return action;
                        }
                    } else {
                        int temp = Math.max(maxValue, maxSecondMove(copyGame, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
                        if (temp > maxValue) {
                            maxValue = temp;
                            bestAction = action;
                        }
                    }
                }
            }
        }
        doneActions++;
        return bestAction;
    }

    private int eval(Game game) {
        Board board = game.getBoard();
        int evaluationValue1=0;
        int evaluationValue2=0;
        int evaluationValue3=0;
        int evaluationValue4=0;

        ArrayList<Action> actions = this.getAllActions(board);
        ArrayList<Action> actionsWhite = game.getWhite().getAllActions(board);

        for(int i=0; i<actions.size(); i++){
            if(actions.get(i).getType() == Action.ActionType.attack){
                evaluationValue1++;
            }
        }
        for(int i=0; i<actionsWhite.size(); i++){
            if(actionsWhite.get(i).getType() == Action.ActionType.attack){
                evaluationValue1--;
//                System.out.println(evaluationValue1);

            }
        }

        int subTotal=0;
        for(int i=0; i<board.getRows().length; i++){
            for(int j=0; j<board.getRows()[i].boardCells.length; j++){
                if(board.getRows()[i].boardCells[j].bead !=null){
                    if(board.getRows()[i].boardCells[j].bead.getPlayer() == this){
                        if(board.getRows()[i].boardCells[j].bead.getType() == BeadType.Tzaars){
                            evaluationValue2 = 3;
                        }
                        else if(board.getRows()[i].boardCells[j].bead.getType() == BeadType.Tzarras){
                            evaluationValue2 = 2;
                        }
                        else if(board.getRows()[i].boardCells[j].bead.getType() == BeadType.Totts){
                            evaluationValue2=1;
                        }

                        evaluationValue3 = board.getRows()[i].boardCells[j].bead.getHeight();
                        subTotal += evaluationValue3*evaluationValue2;

                    } else if(board.getRows()[i].boardCells[j].bead.getPlayer() == game.getWhite()){
                        if(board.getRows()[i].boardCells[j].bead.getType() == BeadType.Tzaars){
                            evaluationValue2 = 3;
                        }
                        else if(board.getRows()[i].boardCells[j].bead.getType() == BeadType.Tzarras){
                            evaluationValue2 = 2;
                        }
                        else if(board.getRows()[i].boardCells[j].bead.getType() == BeadType.Totts){
                            evaluationValue2=1;
                        }
                        evaluationValue3 = board.getRows()[i].boardCells[j].bead.getHeight();
                        subTotal -= evaluationValue3*evaluationValue2;
                    }
                }
            }
        }
        int totalEvaluation = subTotal+evaluationValue1;
        return totalEvaluation;
    }
    private ArrayList<Action> evaluateState(Game game, ArrayList<Action> actions){
        int iiiii=0;
        ArrayList<Action> superiorActions = new ArrayList<>();
        for(Action action: actions){
            Game copyGame = game.copy();
            //-------------------------------
            if (copyGame.applyActionTwo(this, action, true)) {
                continue;
            }
            iiiii++;
            action.setEvaluationValue(eval(copyGame));
            superiorActions.add(action);
        }
//        for(Action theActions: superiorActions){
//            System.out.print(theActions.getEvaluationValue()+" ");
//        }
//        System.out.println();
        Collections.sort(superiorActions);
        ArrayList<Action> choosenOnes = new ArrayList<>();
//        for(Action theActions: superiorActions){
//            System.out.print(theActions.getEvaluationValue()+" ");
//        }
        if(superiorActions.size()>15){
            for(int i=0; i<superiorActions.size()/4;i++){
                choosenOnes.add(superiorActions.get(i));
            }
        } else{
            choosenOnes = superiorActions;
        }
//        System.out.println();
//        System.out.println("ChoosenOnes: " + choosenOnes.size());
//        System.out.println("actions: "+iiiii);

        return choosenOnes;
    }
//    private ArrayList<Action> evaluateState(Game game, ArrayList<Action> actions){
//        ArrayList<Action> superiorActions = new ArrayList<>();
//
//        System.out.println(actions.size());
//        ArrayList<Action> choosenOnes = actions;
//
//        return choosenOnes;
//    }

    @Override
    public Action secondAction(Game game) {
        int maxValue = Integer.MIN_VALUE;
        Action bestAction = null;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        actions = evaluateState(game, actions);
        for (Action action : actions) {
            Game copyGame = game.copy();
            if (copyGame.applyActionTwo(this, action, false)) {
                continue;
            }
            Player winner = copyGame.getWinner();
            if (winner != null) {
                if (winner.getType() == getType()) {
                    return action;
                }
            } else {
                int temp = Math.max(maxValue, minForceAttack(copyGame, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
                if (temp > maxValue) {
                    maxValue = temp;
                    bestAction = action;
                }
            }
        }
        doneActions++;
        return bestAction;
    }

    private int maxForceAttack(Game game, int depth, int alpha, int beta) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int maxValue = Integer.MIN_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
//        for(int i=0; i<actions.size(); i++){
//            if(actions.get(i).getType() == Action.ActionType.attack)
//                actions.remove(i);
//        }
        actions = evaluateState(game, actions);
        for (Action action : actions) {
            if (action.getType() == Action.ActionType.attack) {
                Game copyGame = game.copy();
                if (copyGame.applyActionTwo(this, action, true)) {
                    continue;
                }
                Player winner = copyGame.getWinner();
                if (winner != null) {
                    if (winner.getType() == getType()) {
                        return Integer.MAX_VALUE;
                    }

                } else {
                    maxValue = Math.max(maxValue, maxSecondMove(copyGame, depth + 1, alpha, beta));
                    if(maxValue>=beta){
                        return maxValue;
                    }
                    alpha = Math.max(alpha, maxValue);
                }
            }
        }
        return maxValue;
    }

    private int maxSecondMove(Game game, int depth, int alpha, int beta) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int maxValue = Integer.MIN_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        actions = evaluateState(game, actions);
        for (Action action : actions) {
            Game copyGame = game.copy();
            if (copyGame.applyActionTwo(this, action, false)) {
                continue;
            }
            Player winner = copyGame.getWinner();
            if (winner != null) {
                if (winner.getType() == getType()) {
                    return Integer.MAX_VALUE;
                }
                else {
                    return Integer.MIN_VALUE;
                }
            } else {
                maxValue = Math.max(maxValue, minForceAttack(copyGame, depth + 1, alpha, beta));
                if(maxValue>=beta){
                    return maxValue;
                }
                alpha = Math.max(alpha, maxValue);
            }
        }
        return maxValue;
    }

    private int minForceAttack(Game game, int depth, int alpha, int beta) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int minValue = Integer.MAX_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
//        for(int i=0; i<actions.size(); i++){
//            if(actions.get(i).getType() == Action.ActionType.attack)
//                actions.remove(i);
//        }
        actions = evaluateState(game, actions);
        for (Action action : actions) {
            if (action.getType() == Action.ActionType.attack) {
                Game copyGame = game.copy();
                if (copyGame.applyActionTwo(this, action, true)) {
                    continue;
                }
                Player winner = copyGame.getWinner();
                if (winner != null) {
                    if (winner.getType() == getType().reverse()) {
                        return Integer.MIN_VALUE;
                    }
                } else {
                    minValue = Math.min(minValue, minSecondMove(copyGame, depth + 1, alpha, beta));
                    if(minValue<=alpha){
                        return minValue;
                    }
                    beta = Math.min(minValue, beta);
                }
            }
        }
        return minValue;

    }

    private int minSecondMove(Game game, int depth, int alpha, int beta) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int minValue = Integer.MAX_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        actions = evaluateState(game, actions);
        for (Action action : actions) {
            Game copyGame = game.copy();
            if (copyGame.applyActionTwo(this, action, false)) {
                continue;
            }
            Player winner = copyGame.getWinner();
            if (winner != null) {
                if (winner.getType() == getType().reverse()) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            } else {
                minValue = Math.min(minValue, maxForceAttack(copyGame, depth + 1, alpha, beta));
                if(minValue<=alpha){
                    return minValue;
                }
                beta = Math.min(minValue, beta);
            }
        }

        return minValue;
    }
}
