package MummyMaze;

import agent.Heuristic;

public class HeuristicNumberOfSquares extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state){
        return state.computeNumberOfSquares(problem.getGoalState());
    }

    @Override
    public String toString(){
        return "Tiles distance to final position";
    }
}
