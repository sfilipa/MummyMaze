package MummyMaze;

import agent.Action;

public class ActionDontMove extends Action<MummyMazeState> {
    public ActionDontMove(){
        super(0);
    }

    @Override
    public void execute(MummyMazeState state){
        state.dontMove();//mando o estado não se mexer
        state.setAction(this); //define esta ação como sendo a ação que deu origem ao estado
    }

    @Override
    public boolean isValid(MummyMazeState state){
        return state.cannotMove(); //não se mexe
    }
}
