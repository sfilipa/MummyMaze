package MummyMaze;

import agent.Action;
import agent.Agent;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MummyMazeAgent extends Agent<MummyMazeState> {

    protected MummyMazeState initialEnvironment;
    private List<String> turns;
    private double solutionCost;

    public MummyMazeAgent(MummyMazeState environment) {
        super(environment);
        initialEnvironment = (MummyMazeState) environment.clone();
        //HEURISTICAS
        heuristics.add(new HeuristicNumberOfSquares());
        /*heuristics.add(new HeuristicTilesOutOfPlace());
        heuristic = heuristics.get(0);*/
    }

    public MummyMazeState resetEnvironment(){
        environment = (MummyMazeState) initialEnvironment.clone();
        return environment;
    }

    @Override
    public void executeSolution() {
        for(Action action : solution.getActions()){
            environment.executeAction(action);
        }
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


        for (int i = 0; i < listaStrings.size(); i++) {
//            for (String eachString: listaStrings) {
            matrix[i]= listaStrings.get(i).toCharArray();
//            }
        }

        // debug transformar matriz em turno

        //String turno = MatrixToString(matrix);

        //SolutionPanel.showState(turno);

        initialEnvironment = new MummyMazeState(matrix);
        //System.out.println(initialEnvironment);
        resetEnvironment();
        return environment;
    }

    /*public String MatrixToString(char[][] matrix) {
        String turno = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                turno += matrix[i][j];
            }
            turno += "\n";
        }

        //SolutionPanel.showState(turno);
        return turno;
    }*/


    public List<String> getTurns() {
        return turns;
    }

    public double getSolutionCost(){
        return solutionCost;
    }

}
