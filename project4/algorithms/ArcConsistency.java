/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4.algorithms;

import java.util.LinkedList;
import java.util.PriorityQueue;
import project4.Models.Board;
import project4.Models.Constraints;
import project4.Models.Variable;

/**
 *
 * @author Mohammad
 */
public class ArcConsistency {

    Board board;
    LinkedList<Arc> arcs;
    Constraints constraints;
    boolean[][] result;
    boolean illegal = false;

    public void initialize(Board board, Constraints constraints, boolean[][] result, Variable variable, int value) {
        this.board = board;
        this.constraints = constraints;
        this.result = result;
        arcs = new LinkedList<Arc>();
        for (int i = 0; i < board.getSize(); i++) {
            if (i != variable.getCoordinate().getY()) {
                addArc(variable, board.getMap()[variable.getCoordinate().getX()][i]);
            }
        }
        for (int i = 0; i < board.getSize(); i++) {
            if (i != variable.getCoordinate().getX()) {
                addArc(variable, board.getMap()[i][variable.getCoordinate().getY()]);
            }
        }
    }

    public void addArc(Variable firstVariable, Variable secondVariable) {
        Arc arc = new Arc(firstVariable, secondVariable);
        arcs.add(arc);
    }

    public boolean execute() {
        while (!arcs.isEmpty()) {
            Arc currentArc = arcs.poll();
            Variable firstVariable = currentArc.getFirstVariable();
            Variable secondVariable = currentArc.getSecondVariable();
            if (removeInsconsistentValues(firstVariable, secondVariable)) {
                for (int i = 0; i < board.getMap().length; i++) {
                    if (i != firstVariable.getCoordinate().getY()) {
                        addArc(board.getMap()[firstVariable.getCoordinate().getX()][i], firstVariable);
                    }
                }
                for (int i = 0; i < board.getMap().length; i++) {
                    if (i != firstVariable.getCoordinate().getX()) {
                        addArc(board.getMap()[i][firstVariable.getCoordinate().getY()], firstVariable);
                    }
                }
            } else if (illegal) {
                return false;
            }
        }
        return true;
    }

    public boolean removeInsconsistentValues(Variable firstVariable, Variable secondVariable) {
        boolean removed = false;
        boolean satisfiedValue;
        loop: for (int i = 0; i < firstVariable.getPossibleValues().length; i++) {
            if (constraints.satisfy(board, firstVariable, firstVariable.getPossibleValues()[i])) {
                satisfiedValue = false;
                board.concat(firstVariable, firstVariable.getPossibleValues()[i], result);
                for (int j = 0; j < secondVariable.getPossibleValues().length; j++) {
                    if (constraints.satisfy(board, secondVariable, firstVariable.getPossibleValues()[0])) {
                        satisfiedValue = true;
                    }
                }
                board.separate(firstVariable, firstVariable.getPossibleValues()[0], result);
                if (!satisfiedValue) {
                    board.getMap()[firstVariable.getCoordinate().getX()][firstVariable.getCoordinate().getY()].deleteValue(firstVariable.getPossibleValues()[i]);
                    if (board.getMap()[firstVariable.getCoordinate().getX()][firstVariable.getCoordinate().getY()].getPossibleValues() == null) {
                        illegal = true;
                        return false;
                    }
                    removed = true;
                    break loop;
                }
            }
        }
        return removed;
    }
}

class Arc {

    private Variable firstVariable;
    private Variable secondVariable;

    Arc(Variable firstVariable, Variable secondVariable) {
        setFirstVariable(firstVariable);
        setSecondVariable(secondVariable);
    }

    public Variable getFirstVariable() {
        return firstVariable;
    }

    public void setFirstVariable(Variable firstVariable) {
        this.firstVariable = firstVariable;
    }

    public Variable getSecondVariable() {
        return secondVariable;
    }

    public void setSecondVariable(Variable secondVariable) {
        this.secondVariable = secondVariable;
    }
}
