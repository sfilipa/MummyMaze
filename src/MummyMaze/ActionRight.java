package MummyMaze;

import agent.Action;
import MummyMaze.MummyMazeState;

public class ActionRight extends Action<MummyMazeState>{

    public ActionRight(){
        super(1);
    }

    @Override
    public void execute(MummyMazeState state){
        state.moveRight();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MummyMazeState state){
        return state.canMoveRight();
    }
}