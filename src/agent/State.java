package agent;

public abstract class State{

    /**
     * Action that generated this state.
     */
    protected Action action;

    public abstract void executeAction(Action action);
    
    public Action getAction(){
        return action;
    }

    public void setAction(Action action){
        this.action = action;
    }
}