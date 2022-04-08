package agent;

public abstract class State{ //configuração de um problema

    /**
     * Action that generated this state.
     */
    protected Action action;

    public abstract void executeAction(Action action); //método abstrato que executa a ação
    
    public Action getAction(){
        return action;
    }

    public void setAction(Action action){
        this.action = action;
    }
}