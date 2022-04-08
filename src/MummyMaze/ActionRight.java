package MummyMaze;

import agent.Action;
import eightpuzzle.EightPuzzleState;

public class ActionRight extends Action<EightPuzzleState>{

    public ActionRight(){
        super(1);
    }

    @Override
    public void execute(EightPuzzleState state){
        state.moveRight();
        state.setAction(this);
    }

    @Override
    public boolean isValid(EightPuzzleState state){
        return state.canMoveRight();
    }
}