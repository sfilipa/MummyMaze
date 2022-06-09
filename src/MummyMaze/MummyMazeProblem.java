package MummyMaze;

import agent.Action;
import agent.Problem;

import java.util.ArrayList;
import java.util.List;

public class MummyMazeProblem extends Problem<MummyMazeState> {

    public MummyMazeProblem(MummyMazeState initialState) {
        super(initialState, new ArrayList<>(5));
        super.actions.add(new ActionUp());
        super.actions.add(new ActionRight());
        super.actions.add(new ActionDown());
        super.actions.add(new ActionLeft());
        super.actions.add(new ActionDontMove());

    }

    @Override
    public List<MummyMazeState> executeActions(MummyMazeState state) {

        //Lista de estado sucessores no máx 5
        ArrayList<MummyMazeState> successors = new ArrayList<>(5);
        if (!state.isHeroDead()) {
            //para cada ação disponivel
            for (Action action : actions) {
                //  se a ação for válida
                if (action.isValid(state)) {
                    //criar um novo estado sucessor (igual ao original)
                    MummyMazeState successor = (MummyMazeState) state.clone();
                    //executar a ação sobre o novo estado
                    successor.executeAction(action);
                    //adicionar o novo estado à lista de sucessores
                    successors.add(successor);
                }
            }
        }

        return successors; //devolver a lista de estados sucessores
    }

    @Override
    public boolean isGoal(MummyMazeState state) {
        return state.arrivedToExit();
    }//vê se o state é igual ao objetivo

    @Override
    protected double computePathCost(List<Action> path) {
        return path.size();
    }

}
