package searchmethods;

import agent.State;
import java.util.List;

public class AStarSearch extends InformedSearch {

    //f = g + h
    //copia do greedyBestFirst e só mudar o h para g + h
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        for (State state: successors) {
            //  obter o custo do nó (g)
            Double g = parent.getG() + state.getAction().getCost();
            //se o nó não estiver na fronteira
            if(!frontier.containsState(state)){
                //se o nó não estiver na lista de explorados
                if(!explored.contains(state)){
                    //acrescentar o nó à fronteira (com f = g + h) -> a prioridade e o custo são ambos g + h
                    Double h = heuristic.compute(state);
                    Node node = new Node(state, parent, g, g + h);
                    frontier.add(node);
                }
                //sendo (se o nó estiver na fronteira)
            }else{
                //se este nó tem menor custo que o que está na fronteira
                if(frontier.getNode(state).getG() > g){ //se aquele que está na fronteira tem maior custo que o g
                    //remover o nó que está na fronteira
                    frontier.removeNode(state);
                    //acrescentar o nó à fronteira(com f=g)
                    Double h = heuristic.compute(state);
                    Node node = new Node(state, parent, g, g + h);
                    frontier.add(node);
                }
            }

        }

    }

    @Override
    public String toString() {
        return "A* search";
    }
}
