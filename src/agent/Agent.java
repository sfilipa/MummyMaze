package agent;

import java.util.ArrayList;
import searchmethods.*;

public class Agent<E extends State> {
    //todos os atributos e comportamentos de todos os agentes

    protected E environment; //ambiente - state
    protected ArrayList<SearchMethod> searchMethods;
    protected SearchMethod searchMethod;
    protected ArrayList<Heuristic> heuristics;
    protected Heuristic heuristic;
    protected Solution solution;

    public Agent(E environment) {
        this.environment = environment;
        searchMethods = new ArrayList<>(); //lista de metodos de pesquisa e os vários métodos disponiveis
        searchMethods.add(new BreadthFirstSearch()); //por omissão, procura por largura
        searchMethods.add(new UniformCostSearch());
        searchMethods.add(new DepthFirstSearch());
        searchMethods.add(new DepthLimitedSearch());
        searchMethods.add(new IterativeDeepeningSearch());
        searchMethods.add(new GreedyBestFirstSearch());
        searchMethods.add(new AStarSearch());
        searchMethods.add(new BeamSearch());
        searchMethod = searchMethods.get(0);
        heuristics = new ArrayList<>();
    }

    public Solution solveProblem(Problem problem) { //recebe um problema e devolve uma solução
        if (heuristic != null) { //se tiver definido uma heuristica vai fazer o problema nas heuristicas
            problem.setHeuristic(heuristic);
            heuristic.setProblem(problem);
        }
        long start = System.currentTimeMillis();
        solution = searchMethod.search(problem);
        long end = System.currentTimeMillis();
        searchMethod.getStatistics().setDuration(end - start);
        return solution;
    }

    public void executeSolution() {    
        for(Action action : solution.getActions()){
            environment.executeAction(action);
        }
    }

    public boolean hasSolution() {
        return solution != null;
    }

    public void stop() {
        getSearchMethod().stop();
    }

    public boolean hasBeenStopped() {
        return getSearchMethod().hasBeenStopped();
    }

    public E getEnvironment() {
        return environment;
    }

    public void setEnvironment(E environment) {
        this.environment = environment;
    }

    public SearchMethod[] getSearchMethodsArray() {
        SearchMethod[] sm = new SearchMethod[searchMethods.size()];
        return searchMethods.toArray(sm);
    }

    public SearchMethod getSearchMethod() {
        return searchMethod;
    }

    public void setSearchMethod(SearchMethod searchMethod) {
        this.searchMethod = searchMethod;
    }

    public Heuristic[] getHeuristicsArray() {
        Heuristic[] sm = new Heuristic[heuristics.size()];
        return heuristics.toArray(sm);
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public String getSearchReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(searchMethod + "\n");
        if (solution == null) {
            sb.append("No solution found\n");
        } else {
            sb.append("Solution cost: " + Double.toString(solution.getCost()) + "\n");
        }
        sb.append("Num of expanded nodes: " + searchMethod.getStatistics().numExpandedNodes + "\n");
        sb.append("Max frontier size: " + searchMethod.getStatistics().maxFrontierSize + "\n");
        sb.append("Num of generated nodes: " + searchMethod.getStatistics().numGeneratedNodes+ "\n");
        sb.append("Duration: " + searchMethod.getStatistics().getDurationInSeconds()+ " seconds\n");

        return sb.toString();
    }
}
