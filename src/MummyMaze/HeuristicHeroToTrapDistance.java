package MummyMaze;

import agent.Heuristic;

public class HeuristicHeroToTrapDistance extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state){
        return state.computeHeroToTrapDistance();
    }

    @Override
    public String toString(){
        return "Distance to trap position";
    }
}
