package eightpuzzle;

import java.util.EventObject;

public class EightPuzzleEvent extends EventObject {

    public EightPuzzleEvent(EightPuzzleState source) {
        super(source);
    }
}
