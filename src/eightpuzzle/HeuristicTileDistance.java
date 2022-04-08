package eightpuzzle;

import agent.Heuristic;

public class HeuristicTileDistance extends Heuristic<EightPuzzleProblem, EightPuzzleState>{

    @Override
    public double compute(EightPuzzleState state){
        return state.computeTileDistances(problem.getGoalState());
    }
    
    @Override
    public String toString(){
        return "Tiles distance to final position";
    }
}