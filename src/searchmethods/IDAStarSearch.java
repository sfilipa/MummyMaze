package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;

public class IDAStarSearch extends InformedSearch {
    /*
     * Note that, on each iteration, the search is done in a depth first search way.    
     */
    
    private double limit;
    private double newLimit;    

    @Override
    public Solution search(Problem problem) {
        statistics.reset();        
        stopped = false;

        //TODO

        return null;
    }

    @Override
    protected Solution graphSearch(Problem problem) {

        //TODO

        return null;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        
        for (State est : successors) {
            double g = parent.getG() + est.getAction().getCost();
            if (!frontier.containsState(est)) {
                double f = g + heuristic.compute(est);
                if (f <= limit) {
                    if (!parent.isCycle(est)) {
                        frontier.add(new Node(est, parent, g, f));
                    }
                } else {
                    newLimit = Math.min(newLimit, f);
                }
            } else if (frontier.getNode(est).getG() > g) {
                frontier.removeNode(est);
                frontier.add(new Node(est, parent, g, g + heuristic.compute(est)));
            }
        }
    }

    @Override
    public String toString() {
        return "IDA* search";
    }
}
