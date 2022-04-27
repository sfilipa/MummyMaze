package searchmethods;

import agent.State;
import java.util.List;

import utils.NodePriorityQueue;

public class BeamSearch extends AStarSearch {

    //é uma especie de limite para o AStar

    private int beamSize;

    public BeamSearch() {
        this(100);
    }

    public BeamSearch(int beamSize) {
        this.beamSize = beamSize;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        super.addSuccessorsToFrontier(successors, parent);

        //eliminar os piores nós da fronteira (i.e os com menor prioridade) até a fronteira
        //ficar com tamanho beamSize

        if(frontier.size() > beamSize) {
            NodePriorityQueue aux = new NodePriorityQueue();

            for (int i = 0; i < beamSize; i++) {//iterar beamSize vezes
                aux.add(frontier.remove());
            }
            frontier = aux;
        }
    }

    public void setBeamSize(int beamSize) {
        this.beamSize = beamSize;
    }

    public int getBeamSize() {
        return beamSize;
    }

    @Override
    public String toString() {
        return "Beam search";
    }
}
