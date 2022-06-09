package agent;

import java.util.LinkedList;
import java.util.List;
import searchmethods.Node;

public class Solution {
    private final Problem problem;
    private final LinkedList<Action> actions;

    public Solution(Problem problem, Node goalNode){
        this.problem = problem;
        Node node = goalNode;
        actions = new LinkedList<>();
        while(node.getParent() != null){
            actions.addFirst(node.getState().getAction());
            node = node.getParent();
        }        
    }

    public double getCost(){
        return problem.computePathCost(actions);
    }

    public List<Action> getActions(){
        return actions;
    }
}