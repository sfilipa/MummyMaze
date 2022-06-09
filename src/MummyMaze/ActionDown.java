package MummyMaze;

import agent.Action;
import MummyMaze.MummyMazeState;

public class ActionDown extends Action<MummyMazeState>{

    public ActionDown(){
        super(1);
    }

    @Override
    public void execute(MummyMazeState state){
        state.moveDown();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MummyMazeState state){
        return state.canMoveDown();
    }
}