package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.NodeCollection;

public abstract class GraphSearch<L extends NodeCollection> implements SearchMethod {

    protected L frontier; //fronteira - lista de nós que estão na calha para ser explorados
    protected Set<State> explored = new HashSet<>();//lista de explorados
    protected Statistics statistics = new Statistics();    
    protected boolean stopped; //true se carregarmos no stop

    @Override
    public Solution search(Problem problem) {
        statistics.reset();//reset das estatisticas
        stopped = false;//não está stop
        return graphSearch(problem); //método abaixo
    }

    /* PSEUDOCODIGO DO METODO ABAIXO
     function GRAPH-SEARCH(problem) returns a solution, or failure
        initialize the frontier using the initial state of problem
        initialize the explored set to be empty
        while(frontier is not empty)
            remove the first node from the frontier
            if the node contains a goal state then return the corresponding solution
            add the node to the explored set
            expand the node, adding the resulting nodes to the frontier only if
                not in the frontier or explored set
        return failure
     */
    protected Solution graphSearch(Problem problem) {

        Node node = new Node(problem.getInitialState()); //iniciar um node
        frontier.clear(); //caso estivesse lá coisas
        explored.clear(); // initialize the explored set to be empty

        frontier.add(node); //initialize the frontier using the initial state of problem

        while(!frontier.isEmpty() && !stopped){ //while(frontier is not empty)
            Node frontiernode = frontier.remove(); //remove the first node from the frontier
            if(problem.isGoal(frontiernode.getState())){
                return new Solution(problem, frontiernode);
            }

            explored.add(frontiernode.getState()); // add the node to the explored set

            List<State> successors = problem.executeActions(frontiernode.getState()); //conjunto de nós
            //expand the node, adding the resulting nodes to the frontier only if

            addSuccessorsToFrontier(successors, frontiernode); //adding the resulting nodes to the frontier

            computeStatistics(successors.size());

        }

        return null;
    }

    public abstract void addSuccessorsToFrontier(List<State> successors, Node parent);

    protected void computeStatistics(int successorsSize) {
        statistics.numExpandedNodes++; //numero de nos avaliados
        statistics.numGeneratedNodes += successorsSize; //numero de nos avaliados + sucessores (expandidos)
        statistics.maxFrontierSize = Math.max(statistics.maxFrontierSize, frontier.size());
    }
    
    @Override
    public Statistics getStatistics(){
        return statistics;
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public boolean hasBeenStopped() {
        return stopped;
    }
}
    