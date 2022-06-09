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
        heuristics.add(new HeuristicHeroToExitDistance());
        heuristics.add(new HeuristicEnemyDistances());
        heuristics.add(new HeuristicHeroToTrapDistance());


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
            matrix[i]= listaStrings.get(i).toCharArray();
        }

        initialEnvironment = new MummyMazeState(matrix);
        resetEnvironment();
        return environment;
    }


    public List<String> getTurns() {
        return turns;
    }

    public double getSolutionCost(){
        return solutionCost;
    }

}
