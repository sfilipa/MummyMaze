package eightpuzzle;

import agent.Heuristic;

public class HeuristicTilesOutOfPlace extends Heuristic<EightPuzzleProblem, EightPuzzleState> {

    @Override
    public double compute(EightPuzzleState state) {
        return state.computeTilesOutOfPlace(problem.getGoalState());
    }
    
    @Override
    public String toString(){
        return "Tiles out of place";
    }    
}