package MummyMaze;

import agent.Action;
import eightpuzzle.EightPuzzleState;

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