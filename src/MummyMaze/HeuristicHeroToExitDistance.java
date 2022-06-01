package MummyMaze;

import agent.Heuristic;

public class HeuristicHeroToExitDistance extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.computeHeroToExitDistance();
    }
    
    @Override
    public String toString(){
        return "Distance to exit position";
    }
}