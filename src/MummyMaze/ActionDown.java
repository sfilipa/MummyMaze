package MummyMaze;

import agent.Action;
import MummyMaze.MummyMazeState;

public class ActionDown extends Action<MummyMazeState>{

    public ActionDown(){
        super(1);
    }

    @Override
    public void execute(MummyMazeState state){
        state.moveDown();//mando o estado modificar-se movendo a vazia para baixo
        state.setAction(this); //define esta ação como sendo a ação que deu origem ao estado
    }

    @Override
    public boolean isValid(MummyMazeState state){
        return state.canMoveDown(); //perguntar ao estado se a peça vazia pode mover para baixo
    }
}