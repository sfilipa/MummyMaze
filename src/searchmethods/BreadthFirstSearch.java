package searchmethods;

import agent.State;
import java.util.List;
import utils.NodeLinkedList;

public class BreadthFirstSearch extends GraphSearch<NodeLinkedList> {
    //FIFO

    public BreadthFirstSearch() {
        frontier = new NodeLinkedList();
    } //instanciar a fronteira como lista de nós

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //para cada nó sucessor
        for (State successor: successors) {
            //  se não estiver na fronteira nem nos explorados
            if(!frontier.containsState(successor) && !explored.contains(successor)){
                //      adicionar ao fim da lista ligada
                frontier.addLast(new Node(successor, parent));
            }
        }
    }

    @Override
    public String toString() {
        return "Breadth first search";
    }
}