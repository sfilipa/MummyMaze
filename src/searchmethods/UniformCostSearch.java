package searchmethods;

import agent.State;
import java.util.List;
import utils.NodePriorityQueue;

public class UniformCostSearch extends GraphSearch<NodePriorityQueue> {

    public UniformCostSearch(){
        frontier = new NodePriorityQueue();
    }    
    
    // f = g
    //f = prioridade
    //g = custo -> quanto menor o custo maior a prioridade
    //h = heuristica
    //Há o f=g; ->uniform cost search
    // f=h; -> gredy best first search
    // f=g+h -> A*
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //PSEUDOCODIGO DO METODO ABAIXO
        //para cada nó sucessor
        //  obter o custo do nó (g)
        //      se o nó não estiver na fronteira
        //          se o nó não estiver na lista de explorados
        //              acrescentar o nó à fronteira (com f = g)
        //      sendo (se o nó estiver na fronteira)
        //          se este nó tem menor custo que o que está na fronteira
        //              remover o nó que está na fronteira
        //                  acrescentar o nó à fronteira(com f=g)

        //para cada nó sucessor
        for (State state: successors) {
            //  obter o custo do nó (g)
            Double g = parent.getG() + state.getAction().getCost();
            //se o nó não estiver na fronteira
            if(!frontier.containsState(state)){
                //se o nó não estiver na lista de explorados
                if(!explored.contains(state)){
                    //acrescentar o nó à fronteira (com f = g) -> a prioridade e o custo são ambos g
                    Node node = new Node(state, parent, g, g);
                    frontier.add(node);
                }
                //sendo (se o nó estiver na fronteira)
            }else{
                //se este nó tem menor custo que o que está na fronteira
                if(frontier.getNode(state).getG() > g){ //se aquele que está na fronteira tem maior custo que o g
                    //remover o nó que está na fronteira
                    frontier.removeNode(state);
                    //acrescentar o nó à fronteira(com f=g)
                    Node node = new Node(state, parent, g, g);
                    frontier.add(node);
                }
            }

        }

    }

    @Override
    public String toString() {
        return "Uniform cost search";
    }
}
