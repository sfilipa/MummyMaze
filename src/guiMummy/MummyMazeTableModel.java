package guiMummy;

import MummyMaze.MummyMazeEvent;
import MummyMaze.MummyMazeListener;
import MummyMaze.MummyMazeState;

import javax.swing.table.AbstractTableModel;

public class MummyMazeTableModel extends AbstractTableModel implements MummyMazeListener {


    private MummyMazeState mummyMaze;

    public MummyMazeTableModel(MummyMazeState mummyMaze) {
        if(mummyMaze == null){
            throw new NullPointerException("Puzzle cannot be null");
        }
        this.mummyMaze = mummyMaze;
        this.mummyMaze.addListener(this);
    }

    @Override
    public int getColumnCount() {
        return mummyMaze.getNumLines();
    }

    @Override
    public int getRowCount() {
        return mummyMaze.getNumColumns();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return mummyMaze.getTileValue(row, col);
    }

    @Override
    public void mummyMazeChanged(MummyMazeEvent pe){
        fireTableDataChanged();
        try{
            Thread.sleep(500);
        }catch(InterruptedException ignore){
        }
    }

    public void setMummyMaze(MummyMazeState mummyMaze){
        if(mummyMaze == null){
            throw new NullPointerException("Mummy Maze cannot be null");
        }
        this.mummyMaze.removeListener(this);
        this.mummyMaze = mummyMaze;
        mummyMaze.addListener(this);
        fireTableDataChanged();
    }
}
