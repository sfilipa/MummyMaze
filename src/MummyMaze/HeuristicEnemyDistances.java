package MummyMaze;

import agent.Heuristic;

public class HeuristicEnemyDistances extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state){
        return state.computeEnemyDistances();
    }

    @Override
    public String toString(){
        return "Best path with greatest distance from enemies";
    }
}
