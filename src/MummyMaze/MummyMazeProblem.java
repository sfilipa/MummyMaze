package MummyMaze;

import agent.Action;
import agent.Problem;

import java.util.ArrayList;
import java.util.List;

public class MummyMazeProblem extends Problem<MummyMazeState> {

    //private MummyMazeState goalState; //para o isGoal method

    public MummyMazeProblem(MummyMazeState initialState) {
        super(initialState, new ArrayList<>(5)); //deve ser sempre a primeira linha num construtor
        super.actions.add(new ActionUp());//lista de ações disponiveis
        super.actions.add(new ActionRight());
        super.actions.add(new ActionDown());
        super.actions.add(new ActionLeft());
        super.actions.add(new ActionDontMove());

        //this.goalState = new MummyMazeState(MummyMazeState.GOAL_MATRIX); //iniciar no objetivo(é a constante)

    }

    //devolve a lista de estados sucessores(estados para onde é possivel transitar a partir daquele que
    // é possivel por argumento)
    @Override
    public List<MummyMazeState> executeActions(MummyMazeState state) {

        //Lista de estado sucessores no máx 5
        ArrayList<MummyMazeState> successors = new ArrayList<>(5);

        //para cada ação disponivel
        for(Action action : actions){
            //  se a ação for válida
            if(action.isValid(state)){
                //criar um novo estado sucessor (igual ao original)
                MummyMazeState successor = (MummyMazeState) state.clone();
                //executar a ação sobre o novo estado
                successor.executeAction(action);
                //      adicionar o novo estado à lista de sucessores
                successors.add(successor);
            }
        }
        return successors; //devolver a lista de estados sucessores
    }

    @Override
    public boolean isGoal(MummyMazeState state) {
        return state.chegouASaida();
        //return state.equals(goalState);
    }//vê se o state é igual ao objetivo

    //control + O para override
    @Override
    protected double computePathCost(List<Action> path) {
        return path.size(); //porque as ações têm todas custo 1
    }

    /*public MummyMazeState getGoalState() {
        return goalState;
    }

    public void setGoalState(MummyMazeState goalState) {
        this.goalState = goalState;
    }*/

}
