package MummyMaze;

import MummyMaze.MummyMazeState;

import java.util.EventObject;

public class MummyMazeEvent extends EventObject {

    public MummyMazeEvent(MummyMazeState source) {
        super(source);
    }
}
