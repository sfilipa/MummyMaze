package MummyMaze;

import agent.Agent;
import MummyMaze.MummyMazeState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MummyMazeAgent extends Agent<MummyMazeState> {

    protected MummyMazeState initialEnvironment;

    public MummyMazeAgent(MummyMazeState environment) {
        super(environment);
        initialEnvironment = (MummyMazeState) environment.clone();
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
        List<String> listaStrings = new ArrayList<String>();
        java.util.Scanner scanner = new java.util.Scanner(file);

        String string;

        // checking end of file
        while (scanner.hasNext()) {
            string = scanner.nextLine();

            // adding each string to arraylist
            listaStrings.add(string);
        }


        char[][] matrix = new char [13][13];


        for (int i = 0; i < 13; i++) {
            for (String eachString: listaStrings) {
                matrix[i]= eachString.toCharArray();
            }
        }
        /*


            scanner.nextLine();
        }*/
        initialEnvironment = new MummyMazeState(matrix);
        resetEnvironment();
        return environment;
    }
}
