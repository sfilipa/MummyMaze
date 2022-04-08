package eightpuzzle;

import agent.Action;

public class ActionUp extends Action<EightPuzzleState>{

    public ActionUp(){
        super(1);
    }

    @Override
    public void execute(EightPuzzleState state){
        state.moveUp();
        state.setAction(this);
    }

    @Override
    public boolean isValid(EightPuzzleState state){
        return state.canMoveUp();
    }
}