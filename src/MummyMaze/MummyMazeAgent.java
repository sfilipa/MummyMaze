package MummyMaze;

import agent.Agent;
import MummyMaze.MummyMazeState;

import java.io.File;
import java.io.IOException;

public class MummyMazeAgent extends Agent<MummyMazeState> {

    protected MummyMazeState initialEnvironment;

    public MummyMazeAgent(MummyMazeState environemt) {
        super(environemt);
        initialEnvironment = (MummyMazeState) environemt.clone();
        //HEURISTICAS
        /*heuristics.add(new HeuristicTileDistance());
        heuristics.add(new HeuristicTilesOutOfPlace());
        heuristic = heuristics.get(0);*/
    }

    public MummyMazeState resetEnvironment(){
        environment = (MummyMazeState) initialEnvironment.clone();
        return environment;
    }

    public MummyMazeState readInitialStateFromFile(File file) throws IOException {
        java.util.Scanner scanner = new java.util.Scanner(file);

        char[][] matrix = new char [13][13];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                matrix[i][j] = scanner.next().charAt(i);
            }
            scanner.nextLine();
        }
        initialEnvironment = new MummyMazeState(matrix);
        resetEnvironment();
        return environment;
    }
}
