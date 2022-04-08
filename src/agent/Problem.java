package agent;

import eightpuzzle.EightPuzzleState;

import java.util.List;

public abstract class Problem <S extends State>{

    //TODO

    protected S initialState; //é melhor ser protected por ser uma classe abstrata, S é uma sub classe de State
    protected Heuristic heuristic;
    protected List<Action> actions;//ações possiveis válidas e inválidas

    public Problem(S initialState, List<Action> actions) {
        this.initialState = initialState;
        this.actions = actions;
    }

    public abstract List<S> executeActions(S state); //devolve uma lista de estados

    public abstract boolean isGoal(S state);

    //o custo do caminho,é a soma das ações
    protected double computePathCost(List<Action> path){
        double cost = 0;
        for(Action a : path){
            cost += a.getCost();
        }
        return cost;
    }

    public S getInitialState() {
        return initialState;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
