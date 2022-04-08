package searchmethods;

import agent.State;
import java.util.List;

public class DepthLimitedSearch extends DepthFirstSearch {

    private int limit;

    public DepthLimitedSearch() {
        this(28);
    }

    public DepthLimitedSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        if(parent.getDepth() < limit){ //se a profundidade for menor que o limite, expande. Se for superior pára
            super.addSuccessorsToFrontier(successors, parent);
        }

    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Limited depth first search";
    }
}
