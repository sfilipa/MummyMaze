package MummyMaze;

import java.util.Arrays;
import java.util.Objects;

public class Cell {
    private final int line, column;

    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    //equals, is in mesma linha
    //em vez das linhas para heroi e mumia por as celulas
    //celula so para guardar pos

    public boolean isInSameLine(Cell c){
        if(line == c.line){
            return true;
        }
        return false;
    }

    public boolean isInSameColumn(Cell c){
        if(column == c.column){
            return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return line == cell.line && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}