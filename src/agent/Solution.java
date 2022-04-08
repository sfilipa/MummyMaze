package agent;

import java.util.LinkedList;
import java.util.List;
import searchmethods.Node;

public class Solution {
    private final Problem problem;
    private final LinkedList<Action> actions;//lista de ações

    public Solution(Problem problem, Node goalNode){
        this.problem = problem;
        Node node = goalNode;
        actions = new LinkedList<>();
        while(node.getParent() != null){ //em cada nó pergunta quem é o pai deles
            actions.addFirst(node.getState().getAction());
            node = node.getParent(); //pergunta novamente até chegarmos ao Nó inicial
        }        
    }

    public double getCost(){
        return problem.computePathCost(actions);
    }

    public List<Action> getActions(){
        return actions;
    }
}