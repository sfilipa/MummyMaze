package MummyMaze;

import agent.Heuristic;

public class HeuristicTileDistance extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.computeTileDistances();
    }
    
    @Override
    public String toString(){
        return "Distance to exit position";
    }
}