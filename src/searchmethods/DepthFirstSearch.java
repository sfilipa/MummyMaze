package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;
import utils.NodeLinkedList;

public class DepthFirstSearch extends GraphSearch<NodeLinkedList> {
    //LIFO

    public DepthFirstSearch() {
        frontier = new NodeLinkedList();
    }

    //breadth first Search without explored list
    @Override
    protected Solution graphSearch(Problem problem) {

        Node node = new Node(problem.getInitialState()); //iniciar um node
        frontier.clear(); //caso estivesse lá coisas
        //explored.clear(); // initialize the explored set to be empty
        frontier.add(node); //initialize the frontier using the initial state of problem

        while(!frontier.isEmpty() && !stopped){ //while(frontier is not empty)
            Node frontiernode = frontier.remove(); //remove the first node from the frontier
            if(problem.isGoal(frontiernode.getState())){
                return new Solution(problem, frontiernode);
            }

            //explored.add(frontiernode.getState()); // add the node to the explored set

            List<State> successors = problem.executeActions(frontiernode.getState()); //conjunto de nós
            //expand the node, adding the resulting nodes to the frontier only if

            addSuccessorsToFrontier(successors, frontiernode); //adding the resulting nodes to the frontier

            computeStatistics(successors.size());

        }
        return null;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        //copiado do breadth first mas adaptado por não ser preciso os explorados
        //para cada nó sucessor
        for (State successor: successors) {
            //  se não estiver na fronteira
            if(!frontier.containsState(successor)){
                if(!parent.isCycle(successor)){
                    //      adicionar ao fim da lista ligada
                    frontier.addFirst(new Node(successor, parent));
                }
            }
        }

    }

    @Override
    public String toString() {
        return "Depth first search";
    }
}
