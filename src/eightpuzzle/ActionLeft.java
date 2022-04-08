package eightpuzzle;

import agent.Action;

public class ActionLeft extends Action<EightPuzzleState>{

    public ActionLeft(){
        super(1);
    }

    @Override
    public void execute(EightPuzzleState state){
        state.moveLeft();
        state.setAction(this);
    }

    @Override
    public boolean isValid(EightPuzzleState state){
        return state.canMoveLeft();
    }
}
