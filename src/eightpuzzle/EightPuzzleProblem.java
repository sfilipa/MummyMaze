package eightpuzzle;

import agent.Action;
import agent.Problem;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EightPuzzleProblem extends Problem<EightPuzzleState> {

    private EightPuzzleState goalState; //para o isGoal method

    public EightPuzzleProblem(EightPuzzleState initialState) {
        super(initialState, new ArrayList<>(4)); //deve ser sempre a primeira linha num construtor
        super.actions.add(new ActionUp());//lista de ações disponiveis
        super.actions.add(new ActionRight());
        super.actions.add(new ActionDown());
        super.actions.add(new ActionLeft());

        this.goalState = new EightPuzzleState(EightPuzzleState.GOAL_MATRIX); //iniciar no objetivo(é a constante)

    }

    //devolve a lista de estados sucessores(estados para onde é possivel transitar a partir daquele que
    // é possivel por argumento)
    @Override
    public List<EightPuzzleState> executeActions(EightPuzzleState state) {

        //Lista de estado sucessores no máx 4
       ArrayList<EightPuzzleState> successors = new ArrayList<>(4);

        //para cada ação disponivel
        for(Action action : actions){
            //  se a ação for válida
            if(action.isValid(state)){
                //criar um novo estado sucessor (igual ao original)
                EightPuzzleState successor = (EightPuzzleState) state.clone();
                //executar a ação sobre o novo estado
                successor.executeAction(action);
                //      adicionar o novo estado à lista de sucessores
                successors.add(successor);
            }
        }
        return successors; //devolver a lista de estados sucessores
    }

    @Override
    public boolean isGoal(EightPuzzleState state) {
        return state.equals(goalState);
    }//vê se o state é igual ao objetivo

    //control + O para override
    @Override
    protected double computePathCost(List<Action> path) {
        return path.size(); //porque as ações do EightPuzzleProblem têm todas custo 1
    }

    public EightPuzzleState getGoalState() {
        return goalState;
    }

    public void setGoalState(EightPuzzleState goalState) {
        this.goalState = goalState;
    }

}
