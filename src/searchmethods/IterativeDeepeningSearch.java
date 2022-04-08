package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;

import java.util.List;

public class IterativeDeepeningSearch extends DepthFirstSearch {
    /*
     * We do not use the code from DepthLimitedSearch because we can optimize
     * so that the algorithm only verifies if a state is a goal if its depth is
     * equal to the limit. Note that given a limit X we are sure not to
     * encounter a solution below this limit because a (failed) limited depth
     * search has already been done. That's why we do not extend this class from
     * DepthLimitedSearch. We extend from DepthFirstSearch so that we don't need
     * to rewrite method insertSuccessorsInFrontier again.
     * After the class, please see a version of the search algorithm without
     * this optimization.
     */

    private int limit;

    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        limit = 0;

        //start with limite zero; if solution is not found, tries again
        //with limite += 1

        Solution solution;
        do{
            solution = graphSearch(problem);
            limit++;
            //System.out.println(limit);
        }while(solution == null);

        return solution;
    }

    @Override
    protected Solution graphSearch(Problem problem) {

        //copiado do graphSearch

        Node node = new Node(problem.getInitialState()); //iniciar um node
        frontier.clear(); //caso estivesse lá coisas

        frontier.add(node); //initialize the frontier using the initial state of problem

        while(!frontier.isEmpty() && !stopped){ //while(frontier is not empty)
            Node frontiernode = frontier.remove(); //remove the first node from the frontier
            //only check if a node is the goal if at limit depth -> MUDADO if frontiernode.getDepth() == limit
            if(frontiernode.getDepth() == limit){
                if(problem.isGoal(frontiernode.getState())) { //não expande se for o limite
                    return new Solution(problem, frontiernode);
                }
            }else{ //only expand if node is below limit depth - colocado o resto no else
                List<State> successors = problem.executeActions(frontiernode.getState()); //conjunto de nós
                //expand the node, adding the resulting nodes to the frontier only if

                addSuccessorsToFrontier(successors, frontiernode); //adding the resulting nodes to the frontier

                computeStatistics(successors.size());
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
}


/*
 * 
 public class IterativeDeepeningSearch implements SearchMethod {

    @Override
    public Solution search(Problem problem) {
        DepthLimitedSearch dls = new DepthLimitedSearch();
        Solution solution;
        for (int i = 0;; i++) {
            dls.setLimit(i);
            solution = dls.search(problem);
            if (solution != null) {
                return solution;
            }
        }
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
 *
 */